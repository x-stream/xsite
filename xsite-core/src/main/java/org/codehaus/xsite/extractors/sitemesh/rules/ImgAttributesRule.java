/*
 * Copyright (C) 2013 XSite Committers.
 * All rights reserved.
 *
 * Created on 17.06.2013 by Joerg Schaible
 */
package org.codehaus.xsite.extractors.sitemesh.rules;

import com.opensymphony.module.sitemesh.html.BasicRule;
import com.opensymphony.module.sitemesh.html.CustomTag;
import com.opensymphony.module.sitemesh.html.Tag;

/**
 * Convert attributes appended to the image src into real attributes. The additional attributes
 * are separated with a '#' character, the attribute name is separated by '=' or '+=' from its
 * value. The former will replace an existing attribute, the latter will add the value. 
 * 
 * @author J&ouml;rg Schaible
 */
public class ImgAttributesRule extends BasicRule {

	public ImgAttributesRule() {
		super("img");
	}
	
	@Override
	public void process(Tag tag) {
		if (tag.hasAttribute("src", false)) {
			String base = tag.getAttributeValue("src", false);
			int idx = base.indexOf('#');
			if (idx > 0) {
                CustomTag customTag = new CustomTag(tag);
                customTag.setAttributeValue("src", false, base.substring(0, idx));
                tag = customTag;
                customTag = new CustomTag(tag);
                
				String attrs = base.substring(idx+1);
				while((idx = attrs.indexOf('=')) > 0) {
					String value = "";
					String attr = attrs.substring(0, idx);
					if (attr.endsWith("+")) {
						attr = attr.substring(0, idx-1);
						if (tag.hasAttribute(attr, false)) {
							value =tag.getAttributeValue(attr, false); 
						}
					}
					if (attrs.length() > idx) {
						attrs = attrs.substring(idx+1);
						char ch = attrs.charAt(0);
						if (ch == '"' || ch == '\'') {
							idx = attrs.indexOf(ch, 1);
							value += attrs.substring(1, idx);
							idx = attrs.indexOf('#', idx);
						} else {
							idx = attrs.indexOf('#');
							if (idx < 0) {
								value += attrs;
							} else {
								value = attrs.substring(0, idx+1);
							}
						}
						if (idx < 0) {
							attrs = "";
						} else {
							attrs = attrs.substring(idx+1);
						}
					}
	                customTag.setAttributeValue(attr, false, value);
				}
				if (customTag != null) {
					tag = customTag;
				}
			}
		}
        tag.writeTo(currentBuffer());
	}
}
