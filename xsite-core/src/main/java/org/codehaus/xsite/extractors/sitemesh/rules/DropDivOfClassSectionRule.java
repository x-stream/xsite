/*
 * Copyright (C) 2013 XSite committers.
 * All rights reserved.
 *
 * Created on 30.04.2013 by Joerg Schaible
 */
package org.codehaus.xsite.extractors.sitemesh.rules;

import java.util.ArrayDeque;
import java.util.Deque;

import com.opensymphony.module.sitemesh.html.BasicRule;
import com.opensymphony.module.sitemesh.html.Tag;

/**
 * Special rule for Maven/Doxia generated HTML pages. They nest typically the header paragraphs
 * together with the following elements into a div section with a class attribute of value
 * "section". This rule removes this div elements so that any contained element will be at the
 * same level.  
 * 
 * @author J&ouml;rg Schaible
 */
public class DropDivOfClassSectionRule extends BasicRule {

	private Deque<Boolean> stack;
	
	public DropDivOfClassSectionRule() {
		super("div");
		stack = new ArrayDeque<Boolean>(10);
	}
	
	@Override
	public void process(Tag tag) {
		if (tag.getType() == Tag.OPEN) {
			String value = tag.getAttributeValue("class", false);
			if ("section".equals(value)) {
				stack.push(Boolean.FALSE);
			} else {
				stack.push(Boolean.TRUE);
				tag.writeTo(currentBuffer());
			}
		} else if (tag.getType() == Tag.CLOSE) {
			if (stack.pop()) {
				tag.writeTo(currentBuffer());
			}
		} else {
			tag.writeTo(currentBuffer());
		}
	}

}
