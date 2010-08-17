package org.codehaus.xsite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.junit.Test;

/**
 * @author J&ouml;rg Schaible
 * @author Mauro Talevi
 */
public class MainTest extends AbstractXSiteTest {

    @Test
    public void testCanReadProperties() throws Exception {
        Properties properties = Main.createProperties();
        assertTrue(properties.getProperty("version").startsWith("1."));
    }

    @Test
    public void testCanReadOptionValuesFromCommandLine() throws Exception {
        CommandLine cl = Main.getCommandLine(
                new String[] { "-Ssource", "-msitemap", "-sskin", "-ooutput", "-fpath", "-rresource" }, Main.createOptions());
        assertEquals("source", cl.getOptionValue('S'));
        assertEquals("sitemap", cl.getOptionValue('m'));
        assertEquals("skin", cl.getOptionValue('s'));
        assertEquals("output", cl.getOptionValue('o'));
        assertEquals("path", cl.getOptionValue('f'));
        assertEquals("resource", cl.getOptionValue('r'));
    }

    @Test(expected=Exception.class)
    public void testWillFailWhenBuildingWithoutArgs() throws Exception {
        Main.main(new String[] {});
    }

    @Test
    public void testCanPrintUsage() throws Exception {
        Main.main(new String[] { "-h" });
    }

    @Test
    public void testCanPrintVersion() throws Exception {
        Main.main(new String[] { "-v" });
    }

    @Test
    public void testCanBuildWithDefaultComposition() throws Exception {
        Main.main(new String[] { "-S" + testSrcDir, "-mcontent/sitemap.xml", "-stemplates/skin.html",
                        "-otarget/xsite" });
    }

    @Test
    public void testCanSpecifySkinTemplatesDir() throws Exception {
        Main.main(new String[] { "-S" + testSrcDir, "-mcontent/sitemap.xml", "-sskin.html", "-T"+testSrcDir+"/templates",
                        "-otarget/xsite" });
    }

    @Test
    public void testCanGetCustomCompositionURLViaFile() throws Exception {
        URL url = Main.getCompositionURL(Main.getCommandLine(new String[] { "-S" + testSrcDir, "-mcontent/sitemap.xml",
                "-stemplates/skin.html", "-otarget/xsite", "-fcustom-xsite.xml" }, Main
                .createOptions()));
        assertTrue(url.getPath().endsWith("custom-xsite.xml"));
    }

    @Test
    public void testCanGetCustomCompositionURLViaResource() throws Exception {
        URL url = Main.getCompositionURL(Main.getCommandLine(new String[] { "-S" + testSrcDir, "-mcontent/sitemap.xml",
                "-stemplates/skin.html", "-otarget/xsite", "-rcustom-xsite.xml" }, Main.createOptions()));
        assertTrue(url.getPath().endsWith("custom-xsite.xml"));
    }

    @Test
    public void testCanBuildWithL10N() throws Exception {
        Main.main(new String[] { "-S" + testSrcDir, "-mcontent/sitemap.xml", "-stemplates/skin.html", "-Rresources", "-Lit",
                        "-otarget/xsite" });
    }

}
