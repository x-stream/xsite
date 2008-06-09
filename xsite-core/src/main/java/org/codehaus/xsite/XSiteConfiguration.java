package org.codehaus.xsite;

/**
 * Holds configuration of XSite
 * 
 * @author Mauro Talevi
 */
public class XSiteConfiguration {

    private boolean validateLinks;

    public XSiteConfiguration() {
        this(true);
    }

    public XSiteConfiguration(boolean validateLinks) {
        this.validateLinks = validateLinks;
    }

    public boolean validateLinks() {
        return validateLinks;
    }

}
