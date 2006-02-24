package org.codehaus.xsite;

import java.io.IOException;

/**
 * @author J&ouml;rg Schaible
 */
public class MainTest extends AbstractXSiteTestCase {
    
    public void setUp() throws Exception {
        super.setUp();
    }    
    
    public void testBuild() throws IOException{
        Main.main(new String[] {testSrcDir+"sitemap.xml", testSrcDir+"skin.html", "target/xsite"});
    }



}
