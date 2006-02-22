package org.codehaus.xsite;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.codehaus.xsite.extractors.SiteMeshPageExtractor;
import org.codehaus.xsite.loaders.XStreamSiteMapLoader;
import org.codehaus.xsite.skins.FreemarkerSkin;

public class XSiteTest extends TestCase {

    public void testBuild() throws IOException{
        XSite xsite = new XSite(new XStreamSiteMapLoader(new SiteMeshPageExtractor()),
                new FreemarkerSkin());
       xsite.build(new File("src/test/sitemap.xml"), new File("src/test/skin.html"), new File("target/xsite"));
    }
}
