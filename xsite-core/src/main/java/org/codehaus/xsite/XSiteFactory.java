package org.codehaus.xsite;

import java.util.Map;

/**
 * XSiteFactory creates XSite instances
 * @author Mauro Talevi
 */
public interface XSiteFactory {
    
    /**
     * Creates an XSite instance
     * @param config the Map of configuration required
     * @return An XSite instance
     */
    XSite createXSite(Map config);

}
