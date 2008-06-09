package org.codehaus.xsite;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import org.codehaus.xsite.model.Page;
import org.codehaus.xsite.model.Sitemap;
import org.codehaus.xsite.validators.LinkChecker;

/**
 * Facade for building sites
 * 
 * @author Joe Walnes
 * @author Mauro Talevi
 */
public class XSite {

    private final SitemapLoader siteMapLoader;
    private final Skin skin;
    private final LinkValidator[] validators;
    private final FileSystem fileSystem;
    private final XSiteConfiguration configuration;

    /**
     * Creates an XSite
     * 
     * @param loader the SitemapLoader used to load the Sitemap
     * @param skin the Skin used to skin the pages
     * @param validators the array with the LinkValidator instances
     * @param fileSystem the FileSystem used for IO operations
     * @param configuration the XSite configuration
     */
    public XSite(SitemapLoader loader, Skin skin, LinkValidator[] validators, FileSystem fileSystem,
            XSiteConfiguration configuration) {
        this.siteMapLoader = loader;
        this.skin = skin;
        this.validators = validators;
        this.fileSystem = fileSystem;
        this.configuration = configuration;
    }

    public void build(File sitemapFile, File skinFile, File[] resourceDirs, File outputDirectory) throws IOException {
        // Load sitemap and content
        Sitemap siteMap = siteMapLoader.loadFrom(sitemapFile);

        // Copy resources (css, images, etc) to output
        for (int i = 0; i < resourceDirs.length; i++) {
            File resourceDir = resourceDirs[i];
            System.out.println("Copying resources from " + resourceDir);
            fileSystem.copyDirectory(resourceDir, outputDirectory, true);
        }

        // Apply skin to each page
        skin.load(skinFile);
        outputDirectory.mkdirs();
        for (Iterator iterator = siteMap.getAllPages().iterator(); iterator.hasNext();) {
            Page page = (Page) iterator.next();
            System.out.println("Skinning " + page.getFilename() + " (" + page.getTitle() + ")");
            skin.skin(page, siteMap, outputDirectory);
        }

        // Verify links
        LinkChecker linkChecker = new LinkChecker(siteMap, validators, new LinkChecker.Reporter() {
            public void badLink(Page page, String link) {
                System.err.println("Invalid link on page " + page.getFilename() + " : " + link);
            }
        });

        System.out.println("Validate? "+ configuration.validateLinks());
        if (configuration.validateLinks() && !linkChecker.verify()) {
            System.err.println("Invalid links found with validators " + Arrays.asList(validators));
            System.exit(-1);
        }
    }

}
