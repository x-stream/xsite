package org.codehaus.xsite.model;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Properties;

import org.junit.Test;

/**
 * 
 * @author Mauro Talevi
 */
public class PageTest {

    @Test
    public void canGetPageIdFromProperties(){
        Properties properties = new Properties();
        properties.setProperty("meta.id", "aPageId");
        Page page = new Page("name.html", "head", "body", Collections.EMPTY_LIST, properties);
        assertEquals("aPageId", page.getId());
    }

    @Test
    public void canGetDefaultPageIdFromFilename(){
        Properties properties = new Properties();
        Page page = new Page("name.html", "head", "body", Collections.EMPTY_LIST, properties);
        assertEquals("name", page.getId());
    }
}
