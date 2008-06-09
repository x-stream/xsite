package org.codehaus.xsite.mojo;

import java.io.File;

import junit.framework.TestCase;

import org.apache.maven.plugin.MojoExecutionException;

public class XSiteRunMojoTest  extends TestCase {
    protected  String testSrcDir;
    
    public void setUp() throws Exception {
        setTestDir();
    }    
    
    protected void setTestDir() {
        testSrcDir =  System.getProperty("test.src.dir");
        if ( testSrcDir == null ){
            testSrcDir = "xsite-core/src/test/site";
        } else if ( !testSrcDir.endsWith(File.separator) ){
            testSrcDir = testSrcDir + File.separator; 
        }        
    }

    public void testGoalCanBeRunWithDefaultComposition() throws Exception {
        XSiteRunMojo mojo = new XSiteRunMojo();
        mojo.sourceDirectoryPath = testSrcDir;
        mojo.sitemapPath = "content/sitemap.xml";
        mojo.skinPath = "templates/skin.html";
        mojo.outputDirectoryPath = "target/xsite";
        mojo.execute();
    }

    public void testGoalCanBeRunWithOptionalResources() throws Exception {
        XSiteRunMojo mojo = new XSiteRunMojo();
        mojo.sourceDirectoryPath = testSrcDir;
        mojo.sitemapPath = "content/sitemap.xml";
        mojo.skinPath = "templates/skin.html";
        mojo.resourcePaths = "resources";
        mojo.outputDirectoryPath = "target/xsite";
        mojo.execute();
    }

    public void testGoalFailsWithInvalidPath() throws Exception {
        XSiteRunMojo mojo = new XSiteRunMojo();
        mojo.sourceDirectoryPath = testSrcDir;
        mojo.sitemapPath = "content/non-existent.xml";
        mojo.skinPath = "templates/skin.html";
        mojo.outputDirectoryPath = "target/xsite";
        try {
	    mojo.execute();
	    fail("MojoExecutionException expected");
	} catch ( MojoExecutionException e) {
	    // expected
	}
    }
}

