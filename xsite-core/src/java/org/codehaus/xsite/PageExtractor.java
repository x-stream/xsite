package org.codehaus.xsite;

import java.io.File;

import org.codehaus.xsite.model.Page;

/**
 * Extracts Page content from HTML files
 * 
 * @author Mauro Talevi
 * @author Joe Walnes
 */
public interface PageExtractor {

    Page extractPage(File htmlFile);

    Page extractPage(String filename, String htmlContent);

}