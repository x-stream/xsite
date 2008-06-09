package org.codehaus.xsite;

/**
 * Interface for link validators.
 *
 * @author J&ouml;rg Schaible
 * @since 1.0
 */
public interface LinkValidator {

    /**
     * Test the argument for a valid link
     *
     * @param link the link
     * @return <code>true</code> if valid
     */
    boolean isValid(String link);
}
