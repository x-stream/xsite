/*
 * Copyright (C) 2013 XSite Committers.
 * All rights reserved.
 *
 * Created on 30.04.2013 by Joerg Schaible
 */
package org.codehaus.xsite.extractors.sitemesh.rules;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opensymphony.module.sitemesh.html.BasicRule;
import com.opensymphony.module.sitemesh.html.CustomTag;
import com.opensymphony.module.sitemesh.html.HTMLProcessor;
import com.opensymphony.module.sitemesh.html.Tag;
import com.opensymphony.module.sitemesh.html.rules.PageBuilder;
import com.opensymphony.module.sitemesh.html.util.CharArray;

import org.codehaus.xsite.extractors.SiteMeshPageExtractor.CannotParsePageException;

import org.apache.commons.lang.StringUtils;


/**
 * Rule to extract all top level block elements as separate paragraphs. Normally a body
 * should contain only such elements. In case of other tags or text, those are added as
 * separate div block. 
 * 
 * @author J&ouml;rg Schaible
 */
public class TopLevelBlockExtractingRule extends BasicRule {

	private final static Tag DIV_OPEN = new CustomTag("div", Tag.OPEN); 
	private final static Tag DIV_CLOSE = new CustomTag("div", Tag.CLOSE); 
	private final PageBuilder page;

	public TopLevelBlockExtractingRule(PageBuilder page) {
		super("body");
		this.page = page;
	}

	@Override
	public void process(Tag tag) {
		if (tag.getType() == Tag.CLOSE) {
			extract();
			context.pushBuffer(new CharArray(64));
		} else {
			for (int i = 0; i < tag.getAttributeCount(); i++ ) {
				page.addProperty("body." + tag.getAttributeName(i), tag.getAttributeValue(i));
			}
			currentBuffer().clear();
		}
	}

	private void extract() {
		CharArray bodyBuffer = context.currentBuffer();
		DIV_OPEN.writeTo(bodyBuffer);
		String bodyToScaffold = bodyBuffer.toString();
		bodyBuffer.clear();

		List<CharArray> paragraphs = new ArrayList<CharArray>();
		HTMLProcessor htmlProcessor = new HTMLProcessor(
			bodyToScaffold.toCharArray(), new CharArray(1024));
		InnerRule rule = new InnerRule(paragraphs);
		htmlProcessor.addRule(rule);
		try {
			htmlProcessor.process();
		} catch (IOException e) {
			throw new CannotParsePageException(e);
		}

		page.addProperty("paragraphs", String.valueOf(paragraphs.size()));
		int i = 0;
		for (CharArray paragraph : paragraphs) {
			bodyBuffer.append(paragraph);
			page.addProperty("paragraph." +String.valueOf(i++), paragraph.toString());
		}
	}

	private static class InnerRule extends BasicRule {
		private final static String[] BLOCK_TAGS = {
			"address", "blockquote", "center", "del", "dir", "div", "dl", "fieldset", "form",
			"h1", "h2", "h3", "h4", "h5", "h6", "hr", "ins", "isindex", "menu", "noframes",
			"noscript", "ol", "p", "pre", "table", "ul"};
		private final List<CharArray> paragraphs;
		private int depth;

		private InnerRule(List<CharArray> paragraphs) {
			super(BLOCK_TAGS);
			this.paragraphs = paragraphs;
		}

		@Override
		public boolean shouldProcess(String name) {
			return super.shouldProcess(name);
		}

		@Override
		public void process(Tag tag) {
			int type = tag.getType();
			if (depth == 0 && type == Tag.OPEN) {
				CharArray currentBuffer = currentBuffer();
				if (currentBuffer.length() > 0) {
					String text = StringUtils.stripToNull(currentBuffer.toString());
					if (text != null) {
						CharArray ca = new CharArray(text.length() + 16);
						DIV_OPEN.writeTo(ca);
						ca.append(text);
						DIV_CLOSE.writeTo(ca);
						paragraphs.add(ca);
					}
					currentBuffer.clear();
				}
				tag.writeTo(currentBuffer);
				++depth;
			} else if ((depth == 0 && type == Tag.EMPTY)
					|| (depth == 1 && type == Tag.CLOSE)) {
				if (type == Tag.CLOSE) {
					--depth;
				}
				CharArray currentBuffer = context.popBuffer();
				tag.writeTo(currentBuffer);
				paragraphs.add(currentBuffer);
				context.pushBuffer(new CharArray(1024));
			} else {
				if (type == Tag.OPEN) {
					++depth;
				} else if (type == Tag.CLOSE) {
					--depth;
				}
				tag.writeTo(currentBuffer());
			}
		}
	}
}
