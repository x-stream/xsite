package org.codehaus.xsite.extractors;

import static org.junit.Assert.assertEquals;

import org.codehaus.xsite.extractors.sitemesh.rules.AddClassAttributeToFirstHeaderRule;
import org.codehaus.xsite.extractors.sitemesh.rules.TopLevelBlockExtractingRule;
import org.codehaus.xsite.extractors.sitemesh.rules.DropDivOfClassSectionRule;
import org.codehaus.xsite.extractors.sitemesh.rules.H1ToTitleRule;
import org.codehaus.xsite.io.CommonsFileSystem;
import org.codehaus.xsite.model.Page;
import org.junit.Test;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.module.sitemesh.html.TagRule;
import com.opensymphony.module.sitemesh.html.TextFilter;
import com.opensymphony.module.sitemesh.html.rules.TagReplaceRule;


/**
 * @author J&ouml;rg Schaible
 */
public class SiteMeshPageExtractorTest {

	private SiteMeshPageExtractor pageExtractor;
	{
		CharacterEscaper escaper = new CharacterEscaper();
		final AttributedPageBuilder pageBuilder = new AttributedPageBuilder(escaper);
		pageExtractor = new SiteMeshPageExtractor(
			new TagRule[]{
				new AddClassAttributeToFirstHeaderRule("first"),
				new DropDivOfClassSectionRule(), new TagReplaceRule("ul", "ol"),
				new H1ToTitleRule(pageBuilder), new TopLevelBlockExtractingRule(pageBuilder)},
			new TextFilter[0], new CommonsFileSystem(), escaper, pageBuilder);
	}

	@Test
	public void canExtractPageBodyStartingWithHeader() {
		final String html = "<html><head><title>JUnit</title></head><body><h2>Header</h2></body></html>";
		final Page page = pageExtractor.extractPage("JUnit.html", html);
		assertEquals("JUnit", page.getTitle());
		assertEquals("<h2 class=\"first\">Header</h2>", page.getBody());
	}

	@Test
	public void canExtractPagesWithoutSharingLinksBetweenPages() {
		final String html1 = "<html><head><title>JUnit</title></head><body><a href=\"foo.html\">Header</a></body></html>";
		final String html2 = "<html><head><title>Test</title></head><body><p>Header</p></body></html>";
		final Page page1 = pageExtractor.extractPage("JUnit.html", html1);
		final Page page2 = pageExtractor.extractPage("Test.html", html2);
		assertEquals(1, page1.getLinks().size());
		assertEquals(0, page2.getLinks().size());
		assertEquals("<div><a href=\"foo.html\">Header</a></div>", page1.getBody());
		assertEquals("<p>Header</p>", page2.getBody());
	}

	@Test
	public void canExtractPagesWithReplacedParagraphTags() {
		final String html = "<html><head><title>JUnit</title></head><body><div><ul><li>Header</li></ul></div></body></html>";
		final Page page = pageExtractor.extractPage("JUnit.html", html);
		assertEquals("JUnit", page.getTitle());
		assertEquals("<div><ol><li>Header</li></ol></div>", page.getBody());
	}

	@Test
	public void canExtractPagesWithDroppedDivsOfClassSection() {
		final String html = "<html><head><title>JUnit</title></head><body><div class='section'><h2>H2</h2><div>Text</div><div class='section'><h2>H2</h2><div>Text</div></div></div></body></html>";
		final Page page = pageExtractor.extractPage("JUnit.html", html);
		assertEquals("JUnit", page.getTitle());
		assertEquals(
			"<h2 class=\"first\">H2</h2><div>Text</div><h2>H2</h2><div>Text</div>",
			page.getBody());
	}

	@Test
	public void canExtractPagesUsingH1AsTitleInstead() {
		final String html = "<html><body><h1>JUnit</h1><div>Text</div></body></html>";
		final Page page = pageExtractor.extractPage("JUnit.html", html);
		assertEquals("JUnit", page.getTitle());
		assertEquals("<div>Text</div>", page.getBody());
	}

	@Test
	public void canExtractTopLevelParagraphs() {
		final String html = "<html><body><h1>JUnit</h1>Intro<ul><li>Header</li></ul>In between<p>Next</p><isindex/>Outro</body></html>";
		final Page page = pageExtractor.extractPage("JUnit.html", html);
		assertEquals("JUnit", page.getTitle());
		final String paragraphs[] = {
			"<div>Intro</div>", "<ol><li>Header</li></ol>", "<div>In between</div>",
			"<p>Next</p>", "<isindex/>", "<div>Outro</div>"};
		assertEquals(StringUtils.join(paragraphs), page.getBody());
		String[] paras = page.getParagraphs();
		assertEquals(paragraphs.length, paras.length);
		for (int i = 0; i < paras.length; i++ ) {
			assertEquals(paragraphs[i], paras[i]);
		}
	}
}
