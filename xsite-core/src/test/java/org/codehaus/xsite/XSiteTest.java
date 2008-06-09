package org.codehaus.xsite;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.xsite.factories.PicoXSiteFactory;
import org.junit.Test;

public class XSiteTest extends AbstractXSiteTest {

    @Test
    public void testBuild() throws IOException {
        XSiteFactory factory = new PicoXSiteFactory();
        Map config = new HashMap();
        config.put(URL.class, Thread.currentThread().getContextClassLoader().getResource("org/codehaus/xsite/xsite.xml"));
        XSite xsite = factory.createXSite(config);
        xsite.build(new File(testSrcDir + "/content/sitemap.xml"), new File(testSrcDir + "/templates/skin.html"),
                new File[] { new File(testSrcDir + "/resources"), new File(testSrcDir + "/resources2") }, new File(
                        "target/xsite"));
    }
}
