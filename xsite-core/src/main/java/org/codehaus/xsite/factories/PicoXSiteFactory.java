package org.codehaus.xsite.factories;

import java.net.URL;
import java.util.Map;

import org.codehaus.xsite.Main;
import org.codehaus.xsite.XSite;
import org.codehaus.xsite.XSiteFactory;
import org.picocontainer.PicoContainer;
import org.picocontainer.script.ScriptedContainerBuilder;
import org.picocontainer.script.xml.XMLContainerBuilder;

/**
 * NanoContainer-based implementation of XSiteFactory
 * 
 * @author Mauro Talevi
 */
public class PicoXSiteFactory implements XSiteFactory {

    public XSite createXSite(Map config) {
        URL compositionURL = (URL) config.get(URL.class);
        return instantiateXSite(compositionURL);
    }

    private static XSite instantiateXSite(URL url) {
        ScriptedContainerBuilder builder = new XMLContainerBuilder(url,
                Main.class.getClassLoader());
        PicoContainer container = builder.buildContainer(null, null, false);
        return container.getComponent(XSite.class);
    }

}
