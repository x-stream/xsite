package org.codehaus.xsite;

import java.io.File;
import java.io.IOException;

import org.codehaus.xsite.model.SiteMap;

/**
 * Loads site map content from a configuration file.
 * 
 * @author Mauro Talevi
 * @author Joe Walnes
 */
public interface SiteMapLoader {

    SiteMap loadFrom(File content) throws IOException;

}