package org.codehaus.xsite.factories;

import java.util.Map;

import org.codehaus.xsite.XSite;
import org.codehaus.xsite.XSiteFactory;

/**
 * Default implementation of XSiteFactory which returns default instances of XSite
 * @author Mauro Talevi
 */
public class DefaultXSiteFactory implements XSiteFactory {

    public XSite createXSite(Map config) {
        return new XSite();
    }

}
