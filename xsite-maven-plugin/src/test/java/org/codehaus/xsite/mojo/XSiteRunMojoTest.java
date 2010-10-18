package org.codehaus.xsite.mojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Test;

public class XSiteRunMojoTest {

    @Test
    public void testGoalCanBeRunWithDefaultComposition() throws Exception {
        XSiteRunMojo mojo = new XSiteRunMojo();
        mojo.sourceDirectoryPath = "target/test-classes";
        mojo.sitemapPath = "content/sitemap.xml";
        mojo.skinPath = "templates/skin.html";
        mojo.outputDirectoryPath = "target/xsite";
        mojo.execute();
    }

    @Test
    public void testGoalCanBeRunWithOptionalResources() throws Exception {
        XSiteRunMojo mojo = new XSiteRunMojo();
        mojo.sourceDirectoryPath = "target/test-classes";
        mojo.sitemapPath = "content/sitemap.xml";
        mojo.skinPath = "skin.html";
        mojo.templatesDirectoryPath = "target/test-classes"+"/templates";
        mojo.resourcePaths = "resources";
        mojo.outputDirectoryPath = "target/xsite";
        mojo.execute();
    }
    
    @Test
    public void testGoalCanBeRunWithOptionalCustomProperties() throws Exception {
        XSiteRunMojo mojo = new XSiteRunMojo();
        mojo.sourceDirectoryPath = "target/test-classes";
        mojo.sitemapPath = "content/sitemap.xml";
        mojo.skinPath = "templates/skin.html";
        mojo.outputDirectoryPath = "target/xsite";
        mojo.publishedDate = "10/10/2010";
        mojo.publishedVersion = "1.0";
        mojo.execute();
    }

    @Test(expected=MojoExecutionException.class)
    public void testGoalFailsWithInvalidPath() throws Exception {
        XSiteRunMojo mojo = new XSiteRunMojo();
        mojo.sourceDirectoryPath = "target/test-classes";
        mojo.sitemapPath = "content/non-existent.xml";
        mojo.skinPath = "templates/skin.html";
        mojo.outputDirectoryPath = "target/xsite";
	    mojo.execute();
    }
}

