package org.codehaus.xsite;

import java.io.File;
import java.io.IOException;

import org.codehaus.xsite.model.Sitemap;

/**
 * Loads site map content from a configuration file.
 * 
 * @author Mauro Talevi
 * @author Joe Walnes
 */
public interface SitemapLoader {

    Sitemap loadFrom(File content) throws IOException;

}