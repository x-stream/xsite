package org.codehaus.xsite.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;

/**
 * A single page in a website, including title, filename and content.
 *
 * @author Joe Walnes
 */
public class Page {

    private Properties properties;
    private String filename;
    private String head;
    private String body;
    private Collection links = new HashSet();

    /**
     * @param filename
     * @param head
     * @param body
     * @param links
     * @param properties
     */
    public Page(String filename, String head, String body, Collection links, Properties properties) {
        this.filename = filename;
        this.head = head;
        this.body = body;
        this.links = links;
        this.properties = properties;
    }

    public String getTitle() {
        if (properties.containsKey("meta.short")) {
            return properties.getProperty("meta.short");
        } else {
            return properties.getProperty("title");
        }
    }

    public String getHead() {
        return head.toString();
    }

    public String getBody() {
        return body.toString();
    }

    public String getFilename() {
        return filename;
    }

    public String getHref() {
        return getFilename();
    }

    public Collection getLinks() {
        return Collections.unmodifiableCollection(links);
    }

    public static class CannotParsePageException extends RuntimeException {
        public CannotParsePageException(Throwable cause) {
            super(cause);
        }
    }
    
}
