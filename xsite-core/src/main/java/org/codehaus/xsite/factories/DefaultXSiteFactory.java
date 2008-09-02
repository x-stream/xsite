package org.codehaus.xsite.factories;

import java.util.Map;

import org.codehaus.xsite.LinkValidator;
import org.codehaus.xsite.XSite;
import org.codehaus.xsite.XSiteConfiguration;
import org.codehaus.xsite.XSiteFactory;
import org.codehaus.xsite.extractors.SiteMeshPageExtractor;
import org.codehaus.xsite.io.CommonsFileSystem;
import org.codehaus.xsite.loaders.XStreamSitemapLoader;
import org.codehaus.xsite.skins.FreemarkerSkin;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Implementation of XSiteFactory which returns instances of XSite with default dependencies
 * 
 * @author Mauro Talevi
 */
public class DefaultXSiteFactory implements XSiteFactory {

    public XSite createXSite(Map<?,?> config) {
        return new XSite(new XStreamSitemapLoader(new SiteMeshPageExtractor(), new XStream(new DomDriver())),
                new FreemarkerSkin(), new LinkValidator[0], new CommonsFileSystem(), new XSiteConfiguration());
    }

}
