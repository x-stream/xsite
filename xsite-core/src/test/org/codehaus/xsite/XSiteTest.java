package org.codehaus.xsite;

import java.io.File;
import java.io.IOException;

public class XSiteTest extends AbstractXSiteTestCase {
    
    public void setUp() throws Exception {
        super.setUp();
    }    
    
    public void testBuild() throws IOException{       
        XSite xsite = new XSite();
        xsite.build(new File(testSrcDir+"/content/sitemap.xml"), new File(testSrcDir+"/templates/skin.html"), new File[]{new File(testSrcDir+"/resources"), new File(testSrcDir+"/resources2")}, new File("target/xsite"));
    }
}
