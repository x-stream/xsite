package org.codehaus.xsite;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.xsite.factories.NanoXSiteFactory;

public class XSiteTest extends AbstractXSiteTestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testBuild() throws IOException {
        XSiteFactory factory = new NanoXSiteFactory();
        Map config = new HashMap();
        config.put(URL.class, Thread.currentThread().getContextClassLoader().getResource("org/codehaus/xsite/xsite.xml"));
        XSite xsite = factory.createXSite(config);
        xsite.build(new File(testSrcDir + "/content/sitemap.xml"), new File(testSrcDir + "/templates/skin.html"),
                new File[] { new File(testSrcDir + "/resources"), new File(testSrcDir + "/resources2") }, new File(
                        "target/xsite"));
    }
}
