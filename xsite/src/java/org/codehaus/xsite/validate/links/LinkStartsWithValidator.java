package org.codehaus.xsite.validate.links;

import org.codehaus.xsite.LinkValidator;

/**
 * Validate a link starting with a special value.
 *
 * @author Joe Walnes
 * @author J&ouml;rg Schaible
 * @since 1.0
 */
public class LinkStartsWithValidator implements LinkValidator {

    private final String start;

    public LinkStartsWithValidator(final String start) {
        this.start = start;
    }

    public boolean isValid(String link) {
        return link.startsWith(start);
    }

}
