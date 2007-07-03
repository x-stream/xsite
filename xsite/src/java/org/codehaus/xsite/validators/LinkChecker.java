package org.codehaus.xsite.validators;

import java.util.List;
import java.util.Iterator;
import java.util.Collection;
import java.util.HashSet;

import org.codehaus.xsite.LinkValidator;
import org.codehaus.xsite.model.Page;
import org.codehaus.xsite.model.Sitemap2;


/**
 * Verifies all the links in a Sitemap.
 *
 * @author Joe Walnes
 * @author J&ouml;rg Schaible
 */
public class LinkChecker {

    private final Collection knownPageFileNames;
    private final Sitemap2 siteMap;
    private final Reporter reporter;
    private final LinkValidator[] validators;

    /**
     * Callback for errors.
     */
    public static interface Reporter {
        void badLink(Page page, String link);
    }

    public LinkChecker(Sitemap2 siteMap, LinkValidator[] validators, Reporter reporter) {
        this.siteMap = siteMap;
        this.validators = validators;
        this.reporter = reporter;
        knownPageFileNames = new HashSet();
        List allPages = siteMap.getAllPages();
        for (Iterator iterator = allPages.iterator(); iterator.hasNext();) {
            Page page = (Page) iterator.next();
            knownPageFileNames.add(page.getFilename());
        }
    }

    /**
     * Verifies all the links in the site. Returns true if all links are valid.
     * @return
     */
    public boolean verify() {
        boolean success = true;
        List allPages = siteMap.getAllPages();
        for (Iterator iterator = allPages.iterator(); iterator.hasNext();) {
            Page page = (Page) iterator.next();
            Collection links = page.getLinks();
            for (Iterator iterator1 = links.iterator(); iterator1.hasNext();) {
                String link = (String) iterator1.next();
                if (!verifyLink(link)) {
                    success = false;
                    reporter.badLink(page, link);
                }
            }
        }
        return success;
    }

    protected boolean verifyLink(String link) {
        for (int i = 0; i < validators.length; i++) {
            if (validators[i].isValid(link)) {
                return true;
            }
        }
        int anchorIdx = link.lastIndexOf('#');
        if (anchorIdx >= 0) {
            // todo: Check anchors
            if (anchorIdx == 0) {
                return true;
            }
            link = link.substring(0, link.lastIndexOf('#'));
        }
        if (knownPageFileNames.contains(link)) {
            return true;
        }
        return false;
    }

}
