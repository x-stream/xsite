package org.codehaus.xsite.extractors;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.codehaus.xsite.extractors.sitemesh.filters.MailToLinkTextFilter;
import org.codehaus.xsite.extractors.sitemesh.rules.AddClassAttributeToFirstHeaderRule;
import org.codehaus.xsite.extractors.sitemesh.rules.ImgAttributesRule;
import org.codehaus.xsite.extractors.sitemesh.rules.TopLevelBlockExtractingRule;
import org.codehaus.xsite.extractors.sitemesh.rules.DropDivOfClassSectionRule;
import org.codehaus.xsite.extractors.sitemesh.rules.H1ToTitleRule;
import org.codehaus.xsite.io.CommonsFileSystem;
import org.codehaus.xsite.model.Page;
import org.junit.Test;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.module.sitemesh.html.TagRule;
import com.opensymphony.module.sitemesh.html.TextFilter;
import com.opensymphony.module.sitemesh.html.rules.RegexReplacementTextFilter;
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
				new H1ToTitleRule(pageBuilder), new ImgAttributesRule(),
				new TopLevelBlockExtractingRule(pageBuilder)},
			new TextFilter[]{
				new RegexReplacementTextFilter("%CR", "\n"),
				new MailToLinkTextFilter(Collections.singletonMap("jd", "john.doe@somewhere.moon"))},
			new CommonsFileSystem(), escaper, pageBuilder);
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
		assertEquals("<p><a href=\"foo.html\">Header</a></p>", page1.getBody());
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
			"<p>Intro</p>", "<ol><li>Header</li></ol>", "<p>In between</p>",
			"<p>Next</p>", "<isindex/>", "<p>Outro</p>"};
		assertEquals(StringUtils.join(paragraphs), page.getBody());
		String[] paras = page.getParagraphs();
		assertEquals(paragraphs.length, paras.length);
		for (int i = 0; i < paras.length; i++ ) {
			assertEquals(paragraphs[i], paras[i]);
		}
	}

	@Test
	public void canFilterPageTextAndInjectEmailLink() {
		final String html = "<html><body><h1>JUnit</h1><div>Send an %{MAIL:jd|email|Demo|German Umlaut &#xf6;%CRNext line}% to me!</div></body></html>";
		final Page page = pageExtractor.extractPage("JUnit.html", html);
		assertEquals("JUnit", page.getTitle());
		assertEquals("<div>Send an <a href=\"mailto:john.doe@somewhere.moon?subject=Demo&amp;body=German%20Umlaut%20&#246;%0ANext%20line\">email</a> to me!</div>", page.getBody());
	}

	@Test
	public void canAddImageAttribute() {
		final String html = "<html><body><div><img src='image.png#class=i'/></div></body></html>";
		final Page page = pageExtractor.extractPage("JUnit.html", html);
		assertEquals("<div><img src=\"image.png\" class=\"i\"/></div>", page.getBody());
	}

	@Test
	public void canAppendToImageAttribute() {
		final String html = "<html><body><div><img src='image.png#class+=\" i\"' class=\"x\"/></div></body></html>";
		final Page page = pageExtractor.extractPage("JUnit.html", html);
		assertEquals("<div><img src=\"image.png\" class=\"x i\"/></div>", page.getBody());
	}

	@Test
	public void canAddImageAttributes() {
		final String html = "<html><body><div><img src='image.png#class+=\" i\"#style=\"font:arial; text-color:#112233\"' class=\"x\"/></div></body></html>";
		final Page page = pageExtractor.extractPage("JUnit.html", html);
		assertEquals("<div><img src=\"image.png\" class=\"x i\" style=\"font:arial; text-color:#112233\"/></div>", page.getBody());
	}
}
