package org.codehaus.xsite.validators;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.codehaus.xsite.LinkValidator;
import org.codehaus.xsite.model.Link;
import org.codehaus.xsite.model.Page;
import org.codehaus.xsite.model.Sitemap;


/**
 * Verifies all the links in a Sitemap.
 *
 * @author Joe Walnes
 * @author J&ouml;rg Schaible
 */
public class LinkChecker {

    private final Collection<String> knownPageFileNames;
    private final Sitemap sitemap;
    private final Reporter reporter;
    private final LinkValidator[] validators;

    /**
     * Callback for errors.
     */
    public static interface Reporter {
        void badLink(Page page, String link);
    }

    public LinkChecker(Sitemap sitemap, LinkValidator[] validators, Reporter reporter) {
        this.sitemap = sitemap;
        this.validators = validators;
        this.reporter = reporter;
        knownPageFileNames = new HashSet<String>();
        List<Page> allPages = sitemap.getAllPages();
        for (Page page : allPages ){
            knownPageFileNames.add(page.getFilename());
        }
    }

    /**
     * Verifies all the links in the site. Returns true if all links are valid.
     * @return
     */
    public boolean verify() {
        boolean success = true;
        List<Page> allPages = sitemap.getAllPages();
        for ( Page page : allPages ){
            Collection<Link> links = page.getLinks();
            for ( Link link : links ){
                if (!verifyLinkHref(link.getHref())) {
                    success = false;
                    reporter.badLink(page, link.getHref());
                }
            }
        }
        return success;
    }

    protected boolean verifyLinkHref(String href) {
        for (int i = 0; i < validators.length; i++) {
            if (validators[i].isValid(href)) {
                return true;
            }
        }
        int anchorIdx = href.lastIndexOf('#');
        if (anchorIdx >= 0) {
            // todo: Check anchors
            if (anchorIdx == 0) {
                return true;
            }
            href = href.substring(0, href.lastIndexOf('#'));
        }
        if (knownPageFileNames.contains(href)) {
            return true;
        }
        return false;
    }

}
