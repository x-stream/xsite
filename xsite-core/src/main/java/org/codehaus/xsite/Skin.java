package org.codehaus.xsite;

import java.io.File;
import java.util.Map;

import org.codehaus.xsite.model.Page;
import org.codehaus.xsite.model.Sitemap;

/**
 * Represents a skin, i.e. a template, which is used to process (or "skin") each
 * page.
 * 
 * @author Mauro Talevi
 * @author Joe Walnes
 */
public interface Skin {

	void load(File skinFile);

	void skin(Page page, Sitemap sitemap, File outputDirectory, Map<String, Object> customProperties);

}