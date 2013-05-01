package org.codehaus.xsite.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;


/**
 * @author Mauro Talevi
 * @author J&ouml;rg Schaible
 */
public class PageTest {

	@Test
	public void canGetPageIdFromProperties() {
		Page page = new Page(
			"name.html", "head", "body", new ArrayList<Link>(), Collections.singletonMap(
				"meta.id", "aPageId"));
		assertEquals("aPageId", page.getId());
	}

	@Test
	public void canGetDefaultPageIdFromFilename() {
		Page page = new Page(
			"name.html", "head", "body", new ArrayList<Link>(),
			Collections.<String, String>emptyMap());
		assertEquals("name", page.getId());
	}
}
