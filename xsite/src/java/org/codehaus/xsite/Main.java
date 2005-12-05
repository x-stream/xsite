package org.codehaus.xsite;

import java.io.File;
import java.io.IOException;

import org.codehaus.xsite.extractors.SiteMeshPageExtractor;
import org.codehaus.xsite.loaders.XStreamSiteMapLoader;
import org.codehaus.xsite.skins.FreemarkerSkin;


/**
 * Command line entry point for building org.codehaus.xsite.
 *
 * @author Joe Walnes
 */
public class Main {

    
    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.println("Usage: java " + XSite.class.getName()
                    + " <path-to-org.codehaus.xsite.xml> <path-to-skin> <output-dir>");
            System.exit(-1);
        }
        //TODO use Pico to instantiate XSite
        XSite xsite = new XSite(new XStreamSiteMapLoader(new SiteMeshPageExtractor()), 
                new FreemarkerSkin());
        xsite.build(new File(args[0]), new File(args[1]), new File(args[2]));

    }

}
