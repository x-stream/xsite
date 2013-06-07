/*
 * Copyright (C) 2013 XSite Committers.
 * All rights reserved.
 *
 * Created on 06.06.2013 by Joerg Schaible
 */
package org.codehaus.xsite.extractors.sitemesh.filters;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opensymphony.module.sitemesh.html.TextFilter;

import org.apache.commons.lang.StringUtils;

/**
 * A text filter to inject a mailto: URL.
 * 
 * <p>It provides the following syntax:</p>
 * <pre>
 * %{MAIL:id|link text[|subject text[|body text]]}%
 * </pre>
 * <p>where <em>id</em> is either an email address or an identifier in the map provided with the constructor that
 * should map to an email address, <em>text</em> is the text of the link, <em>subject</em> is the subject of
 * the email and <em>body</em> is the body of the email. The <em>subject</em> and <em>body</em> parameters
 * are optional, but both values will be encoded according RFC 3987.</p>
 * <p>It will be converted into:</p>
 * <pre>
 * &lt;a href=&quot;mailto:email@id.moon?subject=subject%20text&amp;body=body%20text&quot;&gt;link text&lt;/a&gt;
 * </pre>
 * 
 * @author J&ouml;rg Schaible
 * @since upcoming
 */
public class MailToLinkTextFilter implements TextFilter {

	private static final Pattern PATTERN = Pattern.compile("(.*)%\\{MAIL:(.+?)\\|(.+?)(?:\\|(.+?))?(?:\\|(?s)(.+?)(?-s))?\\}%(.*)");
	private final Map<String, String> mailMap;
	private final String classAttribute;

	public MailToLinkTextFilter(Map<String,String> mailMap) {
		this(mailMap, null);
	}

	public MailToLinkTextFilter(Map<String,String> mailMap, String classAttribute) {
		this.mailMap = mailMap;
		this.classAttribute = classAttribute;
	}

	@Override
	public String filter(final String text) {
		Matcher matcher = PATTERN.matcher(text);
		if (matcher.matches()) {
			int groups = matcher.groupCount();
			final StringBuilder sb = new StringBuilder(matcher.group(1));
			sb.append("<a ");
			if (classAttribute != null) {
				sb.append("class=\"").append(classAttribute).append("\" ");
			}
			sb.append("href=\"mailto:");

			final String email = matcher.group(2);
			if (mailMap.containsKey(email)) {
				sb.append(mailMap.get(email));
			} else {
				sb.append(email);
			}
			
			final String title = matcher.group(3);
			String subject = null;
			String body = null;
			if (groups > 4) {
				subject = StringUtils.stripToNull(matcher.group(4));
			}
			if (groups > 5) {
				body = StringUtils.stripToNull(matcher.group(5));
			}
			if (!subject.isEmpty()) {
				sb.append('?').append("subject=");
				encode(sb, subject);
			}
			if (!body.isEmpty()) {
				sb.append(subject.isEmpty() ? '?' : "&amp;");
				sb.append("body=");
				encode(sb, body);
			}
			sb.append("\">").append(title).append("</a>").append(matcher.group(groups));
			
			return sb.toString();
		}
		return text;
	}
	
	private void encode(StringBuilder sb, String s) {
		final int max = s.length();
		for(int i = 0; i < max; ++i) {
			char ch = s.charAt(i);
			if (ch == '&') {
				int idx = s.indexOf(';', i);
				if (idx > 0) {
					String entity = s.substring(i+1, idx);
					if (entity.charAt(0) == '#') {
						if (entity.charAt(1) == 'x') {
							ch = (char)Short.parseShort(entity.substring(2), 16);
						} else {
							ch = (char)Short.parseShort(entity.substring(1));
						}
					}
					i = idx;
				}
			}
			if (":/?#[]@!$&'()*+,;=\"%".indexOf(ch) < 0 
					&& !Character.isISOControl(ch) 
					&& !Character.isWhitespace(ch)) {
				sb.append(ch);
			} else {
				final String hex = Integer.toHexString(ch).toUpperCase();
				sb.append('%');
				if (hex.length() % 2 == 1) {
					sb.append('0');
				}
				sb.append(hex);
			}
		}
	}
}