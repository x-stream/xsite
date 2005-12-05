package org.codehaus.xsite;

import java.io.File;

import org.codehaus.xsite.model.Page;
import org.codehaus.xsite.model.SiteMap;

/**
 * Interface that allows the loading of a skin - ie a template
 * file for the website pages - and to skin each page.
 * 
 * @author Mauro Talevi
 * @author Joe Walnes
 */
public interface Skin {

    public void load(File skinFile);
    
    void skin(Page page, SiteMap siteMap, File outputDirectory);

}