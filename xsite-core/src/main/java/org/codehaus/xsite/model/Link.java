package org.codehaus.xsite.model;

/**
 * Represents a link entry in a sitemap, including title and href.
 * 
 * @author Joe Walnes
 * @author Mauro Talevi
 */
public class Link extends Entry {

    private final String title;
    private final String href;

    public Link(String title, String href) {
        this.title = title;
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public String getHref() {
        return href;
    }
}
