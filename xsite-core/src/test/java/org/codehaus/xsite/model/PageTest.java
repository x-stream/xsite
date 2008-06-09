package org.codehaus.xsite.model;

import java.util.Collections;
import java.util.Properties;

import junit.framework.TestCase;

/**
 * 
 * @author Mauro Talevi
 */
public class PageTest extends TestCase {

    public void testCanGetPageIdFromProperties(){
        Properties properties = new Properties();
        properties.setProperty("meta.id", "aPageId");
        Page page = new Page("name.html", "head", "body", Collections.EMPTY_LIST, properties);
        assertEquals("aPageId", page.getId());
    }

    public void testCanGetDefaultPageIdFromFilename(){
        Properties properties = new Properties();
        Page page = new Page("name.html", "head", "body", Collections.EMPTY_LIST, properties);
        assertEquals("name", page.getId());
    }
}
