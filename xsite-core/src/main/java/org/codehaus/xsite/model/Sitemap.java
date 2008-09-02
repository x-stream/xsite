package org.codehaus.xsite.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Holds the structure of a website.
 *
 * @author Joe Walnes
 * @author Mauro Talevi
 */
public class Sitemap {

    private List<Section> sections = new ArrayList<Section>();

    public void addSection(Section section) {
        sections.add(section);
    }

    public List<Section> getSections() {
        return Collections.unmodifiableList(sections);
    }

    public List<Entry> getAllEntries() {
        List<Entry> list = new ArrayList<Entry>();
        for (Section section : sections ){
            for ( Entry entry : section.getEntries() ){
                list.add(entry);
            }
        }
        return Collections.unmodifiableList(list);
    }

    public List<Page> getAllPages() {
        List<Page> list = new ArrayList<Page>();
        for (Section section : sections ){
            for ( Page page : section.getPages() ){
                list.add(page);
            }
        }
        return Collections.unmodifiableList(list);
    }
}
