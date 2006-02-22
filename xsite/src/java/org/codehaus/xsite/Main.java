package org.codehaus.xsite;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;

import org.codehaus.xsite.extractors.SiteMeshPageExtractor;
import org.codehaus.xsite.loaders.XStreamSiteMapLoader;
import org.codehaus.xsite.skins.FreemarkerSkin;
import org.nanocontainer.script.ScriptedContainerBuilder;
import org.nanocontainer.script.xml.XMLContainerBuilder;
import org.picocontainer.PicoContainer;
import org.picocontainer.defaults.ObjectReference;
import org.picocontainer.defaults.SimpleReference;


/**
 * Command line entry point for building org.codehaus.xsite.
 *
 * @author Joe Walnes
 * @author J&ouml;rg Schaible
 */
public class Main {

    // TODO use CLI
    private static String XSITE_FILE = "xsite.xml";

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.println("Usage: java " + XSite.class.getName()
                    + " <path-to-org.codehaus.xsite.xml> <path-to-skin> <output-dir>");
            System.exit(-1);
        }

        final URL url;
        File xsiteFile = new File(XSITE_FILE);
        if (xsiteFile.exists()) {
            url = xsiteFile.toURL();
        } else {
            url = Main.class.getClassLoader().getResource(XSITE_FILE);
        }

        ScriptedContainerBuilder builder = new XMLContainerBuilder(url, Main.class.getClassLoader());
        ObjectReference reference = new SimpleReference();
        builder.buildContainer(reference, null, null, false);
        PicoContainer pico = (PicoContainer)reference.get();

        XSite xsite = (XSite)pico.getComponentInstancesOfType(XSite.class);
        xsite.build(new File(args[0]), new File(args[1]), new File(args[2]));
    }

}
