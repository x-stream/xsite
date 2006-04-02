package org.codehaus.xsite.mojo;

import java.io.File;

import junit.framework.TestCase;

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
    
    public void testGoalCanBeRun() throws Exception {
        XSiteRunMojo mojo = new XSiteRunMojo();
        mojo.siteMapPath = testSrcDir+"content/website.xml";
        mojo.skinPath = testSrcDir+"templates/skin.html";
        mojo.outputDirectoryPath = "target/xsite";
        mojo.execute();
    }

}

