package org.codehaus.xsite;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;

public class XSiteTest extends TestCase {

    public void testBuild() throws IOException{
        XSite xsite = new XSite();
       xsite.build(new File("src/test/sitemap.xml"), new File("src/test/skin.html"), new File("target/xsite"));
    }
}
