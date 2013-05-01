package org.codehaus.xsite.extractors.sitemesh.rules;

import com.opensymphony.module.sitemesh.html.BasicRule;
import com.opensymphony.module.sitemesh.html.CustomTag;
import com.opensymphony.module.sitemesh.html.Tag;

/**
 * Rule for HTMLProcessor that adds a class attribute to the first header of the body if it is the first element.
 * The attribute value is by default "FirstChild".
 */
public class AddFirstChildClassToHeader extends BasicRule {
    private boolean firstChild = false;
	private final String className;

    public AddFirstChildClassToHeader() {
    	this("FirstChild");
    }

    public AddFirstChildClassToHeader(String className) {
    	super(new String[]{"p", "div", "h1", "h2", "h3", "h4", "h5", "h6"});
		this.className = className;
    }

    public void process(Tag tag) {
        if (!firstChild) {
            if (tag.getName().charAt(0) == 'h') {
                final CustomTag customTag = new CustomTag(tag);
                customTag.addAttribute("class", className);
                tag = customTag;
            }
            firstChild = true;
        }
        tag.writeTo(currentBuffer());
    }
}