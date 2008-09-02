package org.codehaus.xsite.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A section in a website holding entries, which can be either pages or links.
 *
 * @author Joe Walnes
 */
public class Section {

    private final String name;
    private final List<Entry> entries = new ArrayList<Entry>();

    public Section(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Page> getPages() {
        List<Page> pages = new ArrayList<Page>();
        for ( Entry entry : entries ){
            if ( entry instanceof Page ){
                pages.add((Page)entry);
            }
        }
        return Collections.unmodifiableList(pages);
    }

    public void addPage(Page page) {
        entries.add(page);
    }
}
