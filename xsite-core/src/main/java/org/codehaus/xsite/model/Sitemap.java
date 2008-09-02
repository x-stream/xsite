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

    public List<Page> getAllPages() {
        List<Page> result = new ArrayList<Page>();
        for (Section section : sections ){
            for ( Page page : section.getPages() ){
                result.add(page);
            }
        }
        return Collections.unmodifiableList(result);
    }
}
