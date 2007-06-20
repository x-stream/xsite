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
            testSrcDir = "xsite-maven-plugin/src/test/"; 
        } else if ( !testSrcDir.endsWith(File.separator) ){
            testSrcDir = testSrcDir + File.separator; 
        }        
    }

    public void testGoalCanBeRunWithDefaultComposition() throws Exception {
        XSiteRunMojo mojo = new XSiteRunMojo();
        mojo.siteMapPath = testSrcDir+"content/website.xml";
        mojo.skinPath = testSrcDir+"templates/skin.html";
        mojo.outputDirectoryPath = "target/xsite";
        mojo.execute();
    }

    public void testGoalCanBeRunWithOptionalResources() throws Exception {
        XSiteRunMojo mojo = new XSiteRunMojo();
        mojo.siteMapPath = testSrcDir+"content/website.xml";
        mojo.skinPath = testSrcDir+"templates/skin.html";
        mojo.resourcePaths = testSrcDir+"resources";
        mojo.outputDirectoryPath = "target/xsite";
        mojo.execute();
    }

    public void testGoalCanBeRunWithOptionalFileComposition() throws Exception {
        XSiteRunMojo mojo = new XSiteRunMojo();
        mojo.siteMapPath = testSrcDir+"content/website.xml";
        mojo.skinPath = testSrcDir+"templates/skin.html";
        mojo.outputDirectoryPath = "target/xsite";
        mojo.compositionFilePath = testSrcDir+"templates/xsite.xml";
        mojo.execute();
    }

    public void testGoalCanBeRunWithOptionalResourceComposition() throws Exception {
        XSiteRunMojo mojo = new XSiteRunMojo();
        mojo.siteMapPath = testSrcDir+"content/website.xml";
        mojo.skinPath = testSrcDir+"templates/skin.html";
        mojo.outputDirectoryPath = "target/xsite";
        mojo.compositionResourcePath = "templates/xsite.xml";
        mojo.execute();
    }
    
    public void testGoalFailsWithInvalidPath() throws Exception {
        XSiteRunMojo mojo = new XSiteRunMojo();
        mojo.siteMapPath = testSrcDir+"content/non-existent.xml";
        mojo.skinPath = testSrcDir+"templates/skin.html";
        mojo.outputDirectoryPath = "target/xsite";
        try {
	    mojo.execute();
	    fail("MojoExecutionException expected");
	} catch ( MojoExecutionException e) {
	    // expected
	}
    }
}

