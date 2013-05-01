/*
 * Copyright (C) 2013 XSite Committers.
 * All rights reserved.
 *
 * Created on 01.05.2013 by Joerg Schaible
 */
package org.codehaus.xsite.extractors.sitemesh.rules;

import com.opensymphony.module.sitemesh.html.BlockExtractingRule;
import com.opensymphony.module.sitemesh.html.Tag;
import com.opensymphony.module.sitemesh.html.rules.PageBuilder;

/**
 * Treat the content of the first H1 tag as page title and drop the element instead. Rule is
 * interesting for pages generated with plain Markdown, since it is not possible to define a
 * title. Skins can reuse the title to generate an own H1 element at a prominent place if
 * required.
 * 
 * @author J&ouml;rg Schaible
 */
public class H1ToTitleRule extends BlockExtractingRule {

	private final PageBuilder page;
	private boolean seenTitle;

	public H1ToTitleRule(PageBuilder page) {
		super(false, "h1");
		this.page = page;
	}

	@Override
	protected void end(Tag tag) {
        if (!seenTitle) {
            page.addProperty("title", currentBuffer().toString());
            seenTitle = true;
        }
	}
}
