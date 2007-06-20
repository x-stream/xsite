package org.codehaus.xsite;

import java.net.URL;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.codehaus.xsite.factories.DefaultXSiteFactory;


/**
 * @author J&ouml;rg Schaible
 * @author Mauro Talevi
 */
public class MainTest extends AbstractXSiteTestCase {
    
    public void setUp() throws Exception {
        super.setUp();
    }    
    
    public void testCanReadProperties() throws Exception {
        Properties properties = Main.createProperties();
        assertEquals("1.0", properties.getProperty("version"));
    }
    
    public void testCanReadOptionValuesFromCommandLine() throws Exception {
        CommandLine cl = Main.getCommandLine(new String[]{"-msitemap", "-sskin", "-ooutput",  "-fpath", "-rresource"}, 
                                             Main.createOptions());
        assertEquals("sitemap", cl.getOptionValue('m'));
        assertEquals("skin", cl.getOptionValue('s'));
        assertEquals("output", cl.getOptionValue('o'));
        assertEquals("path", cl.getOptionValue('f'));
        assertEquals("resource", cl.getOptionValue('r'));
    }
    
    public void testWillFailWhenBuildingWithoutArgs() {   
        try {
	    Main.main(new String[] {});
	    fail("Expected");
	} catch (Exception e) {
	    // expected
	}
    }
    
    public void testCanPrintUsage() throws Exception{        
        Main.main(new String[] {"-h"});
    }
    
    public void testCanPrintVersion() throws Exception{        
        Main.main(new String[] {"-v"});
    }

    public void testCanBuildWithDefaultComposition() throws Exception{        
        Main.main(new String[] {"-m"+testSrcDir+"/content/sitemap.xml", "-s"+ testSrcDir+"/templates/skin.html", 
            "-otarget/xsite"});
    }
    
    public void testCanGetCustomCompositionURLViaFile() throws Exception{        
        URL url = Main.getCompositionURL(Main.getCommandLine(new String[] {"-m"+testSrcDir+"/content/sitemap.xml", "-s"+ testSrcDir+"/templates/skin.html", 
                "-otarget/xsite", "-f"+testSrcDir+"custom-xsite.xml"}, Main.createOptions()));       
        assertTrue(url.getPath().endsWith("custom-xsite.xml"));
    }
    
    public void testCanGetCustomCompositionURLViaResource() throws Exception{        
        URL url = Main.getCompositionURL(Main.getCommandLine(new String[] {"-m"+testSrcDir+"/content/sitemap.xml", "-s"+ testSrcDir+"/templates/skin.html", 
                "-otarget/xsite", "-rcustom-xsite.xml"}, Main.createOptions()));       
        assertTrue(url.getPath().endsWith("custom-xsite.xml"));
    }

    public void testCanBuildWithCustomXSiteFactory() throws Exception{
        Main.main(new String[] {"-x"+DefaultXSiteFactory.class.getName(),
                "-m"+testSrcDir+"/content/sitemap.xml", "-s"+testSrcDir+"/templates/skin.html", 
                "-otarget/xsite"});

    }
}
