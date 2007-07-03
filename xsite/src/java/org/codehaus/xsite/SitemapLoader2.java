package org.codehaus.xsite;

import java.io.File;
import java.io.IOException;

import org.codehaus.xsite.model.Sitemap2;

/**
 * Loads site map content from a configuration file.
 * 
 * @author Mauro Talevi
 * @author Joe Walnes
 */
public interface SitemapLoader2 {

    Sitemap2 loadFrom(File content) throws IOException;

}