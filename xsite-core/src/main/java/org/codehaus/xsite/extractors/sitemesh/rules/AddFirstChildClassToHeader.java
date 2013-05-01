package org.codehaus.xsite.extractors.sitemesh.rules;

/**
 * Rule for HTMLProcessor that adds a class attribute with value "FirstChild" to the first
 * header of the body if it is the first element.
 * 
 * @deprecated
 */
@Deprecated
public class AddFirstChildClassToHeader extends AddClassAttributeToFirstHeaderRule {
	@Deprecated
	public AddFirstChildClassToHeader() {
		super("FirstChild");
		System.out.println(AddFirstChildClassToHeader.class.getName()
			+ " is deprecated, please use "
			+ AddClassAttributeToFirstHeaderRule.class.getName()
			+ " instead");
	}
}
