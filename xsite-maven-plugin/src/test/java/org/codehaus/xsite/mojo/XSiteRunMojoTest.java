package org.codehaus.xsite.mojo;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Before;
import org.junit.Test;

public class XSiteRunMojoTest {
    protected  String testSrcDir;
    
    @Before
    public void setUp() throws Exception {
        testSrcDir =  System.getProperty("test.src.dir");
        if ( testSrcDir == null ){
            testSrcDir = "xsite-core/src/test/site";
        } else if ( !testSrcDir.endsWith(File.separator) ){
            testSrcDir = testSrcDir + File.separator; 
        }        
    }

    @Test
    public void testGoalCanBeRunWithDefaultComposition() throws Exception {
        XSiteRunMojo mojo = new XSiteRunMojo();
        mojo.sourceDirectoryPath = testSrcDir;
        mojo.sitemapPath = "content/sitemap.xml";
        mojo.skinPath = "templates/skin.html";
        mojo.outputDirectoryPath = "target/xsite";
        mojo.execute();
    }

    @Test
    public void testGoalCanBeRunWithOptionalResources() throws Exception {
        XSiteRunMojo mojo = new XSiteRunMojo();
        mojo.sourceDirectoryPath = testSrcDir;
        mojo.sitemapPath = "content/sitemap.xml";
        mojo.skinPath = "templates/skin.html";
        mojo.resourcePaths = "resources";
        mojo.outputDirectoryPath = "target/xsite";
        mojo.execute();
    }
    
    @Test
    public void testGoalCanBeRunWithOptionalCustomProperties() throws Exception {
        XSiteRunMojo mojo = new XSiteRunMojo();
        mojo.sourceDirectoryPath = testSrcDir;
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
        mojo.sourceDirectoryPath = testSrcDir;
        mojo.sitemapPath = "content/non-existent.xml";
        mojo.skinPath = "templates/skin.html";
        mojo.outputDirectoryPath = "target/xsite";
	    mojo.execute();
    }
}

