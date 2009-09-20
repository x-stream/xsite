package org.codehaus.xsite;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.xsite.factories.PicoXSiteFactory;
import org.codehaus.xsite.io.CommonsFileSystem.FileSystemException;
import org.junit.Test;

public class XSiteTest extends AbstractXSiteTest {

    @Test
    public void testBuild() throws IOException {
        XSiteFactory factory = new PicoXSiteFactory();
        Map<Class<URL>, URL> config = new HashMap<Class<URL>, URL>();
        config.put(URL.class, Thread.currentThread().getContextClassLoader().getResource("org/codehaus/xsite/xsite.xml"));
        XSite xsite = factory.createXSite(config);
        xsite.build(new File(testSrcDir + "/content/sitemap.xml"), new File(testSrcDir + "/templates/skin.html"),
                new File[] { new File(testSrcDir + "/resources"), new File(testSrcDir + "/resources2") }, new File(
                        "target/xsite"), new HashMap<String, Object>());
    }
    
    @Test(expected=FileSystemException.class)
    public void testBuildWithInexistentResources() throws IOException {
        XSiteFactory factory = new PicoXSiteFactory();
        Map<Class<URL>, URL> config = new HashMap<Class<URL>, URL>();
        config.put(URL.class, Thread.currentThread().getContextClassLoader().getResource("org/codehaus/xsite/xsite.xml"));
        XSite xsite = factory.createXSite(config);
        xsite.build(new File(testSrcDir + "/content/sitemap.xml"), new File(testSrcDir + "/templates/skin.html"),
                new File[] { new File(testSrcDir + "/inexistent-resources") }, new File(
                        "target/xsite"), new HashMap<String, Object>());
    }
}
