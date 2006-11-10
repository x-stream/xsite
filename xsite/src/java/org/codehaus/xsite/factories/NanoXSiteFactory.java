package org.codehaus.xsite.factories;

import java.net.URL;
import java.util.Map;

import org.codehaus.xsite.Main;
import org.codehaus.xsite.XSite;
import org.codehaus.xsite.XSiteFactory;
import org.nanocontainer.script.ScriptedContainerBuilder;
import org.nanocontainer.script.xml.XMLContainerBuilder;
import org.picocontainer.PicoContainer;
import org.picocontainer.defaults.ObjectReference;
import org.picocontainer.defaults.SimpleReference;

/**
 * NanoContainer-based implementation of XSiteFactory
 * 
 * @author Mauro Talevi
 */
public class NanoXSiteFactory implements XSiteFactory {

    public XSite createXSite(Map config) {
        URL compositionURL = (URL) config.get(URL.class);
        return instantiateXSite(compositionURL);
    }

    private static XSite instantiateXSite(URL url) {
        ScriptedContainerBuilder builder = new XMLContainerBuilder(url,
                Main.class.getClassLoader());
        ObjectReference reference = new SimpleReference();
        builder.buildContainer(reference, null, null, false);
        PicoContainer pico = (PicoContainer) reference.get();
        return (XSite) pico.getComponentInstanceOfType(XSite.class);
    }

}
