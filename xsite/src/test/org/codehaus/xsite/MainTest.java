package org.codehaus.xsite;

import junit.framework.TestCase;

import java.io.IOException;

/**
 * @author J&ouml;rg Schaible
 */
public class MainTest extends TestCase {

    public void testBuild() throws IOException{
        Main.main(new String[] {"src/test/sitemap.xml", "src/test/skin.html", "target/xsite"});
    }

}
