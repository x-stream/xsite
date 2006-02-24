package org.codehaus.xsite;

import java.io.File;
import java.io.IOException;

public class XSiteTest extends AbstractXSiteTestCase {
    
    public void setUp() throws Exception {
        super.setUp();
    }    
    
    public void testBuild() throws IOException{       
        XSite xsite = new XSite();
        xsite.build(new File(testSrcDir+"sitemap.xml"), new File(testSrcDir+"skin.html"), new File("target/xsite"));
    }
}
