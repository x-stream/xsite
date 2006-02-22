package org.codehaus.xsite;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.codehaus.xsite.extractors.SiteMeshPageExtractor;
import org.codehaus.xsite.io.FileSystem;
import org.codehaus.xsite.loaders.XStreamSiteMapLoader;
import org.codehaus.xsite.model.Page;
import org.codehaus.xsite.model.SiteMap;
import org.codehaus.xsite.skins.FreemarkerSkin;
import org.codehaus.xsite.validate.LinkChecker;


/**
 * Facade for building sites
 *
 * @author Joe Walnes
 * @author Mauro Talevi
 */
public class XSite {

    private SiteMapLoader siteMapLoader;
    private Skin skin;
    private final LinkValidator[] validators;

    /**
     * Creates an XSite with default dependencies
     */
    public XSite() {
        this(new XStreamSiteMapLoader(new SiteMeshPageExtractor()),
                new FreemarkerSkin(), new LinkValidator[0]);
    }

    /**
     * Creates an XSite with custom dependencies
     * @param loader the SiteMapLoader used to load the SiteMap
     * @param skin the Skin used to skin the pages
     * @param validators the array with the LinkValidator instances
     */
    public XSite(SiteMapLoader loader, Skin skin, LinkValidator[] validators) {
        this.siteMapLoader = loader;
        this.skin = skin;
        this.validators = validators;
    }

    public void build(File siteMapFile, File skinFile, File outputDirectory) throws IOException{
        // Load sitemap and content
        SiteMap siteMap = siteMapLoader.loadFrom(siteMapFile);

        // Apply skin to each page
        skin.load(skinFile);
        outputDirectory.mkdirs();
        for (Iterator iterator = siteMap.getAllPages().iterator(); iterator.hasNext();) {
            Page page = (Page) iterator.next();
            System.out.println("Skinning " + page.getFilename() + " (" + page.getTitle() + ")");
            skin.skin(page, siteMap, outputDirectory);
        }

        // Copy additional resources (css, images, etc) to output
        FileSystem fileSystem = new FileSystem();
        fileSystem.copyAllFiles(siteMapFile.getParentFile(), outputDirectory, "html,xml");
        fileSystem.copyAllFiles(skinFile.getParentFile(), outputDirectory, "html,xml");

        // Verify links
        LinkChecker linkChecker = new LinkChecker(siteMap, validators, new LinkChecker.Reporter() {
            public void badLink(Page page, String link) {
                System.err.println("Invalid link on page " + page.getFilename() + " : " + link);
            }
        });
        if (!linkChecker.verify()) {
            System.err.println("INVALID LINKS FOUND");
            System.exit(-1);
        }
    }

}
