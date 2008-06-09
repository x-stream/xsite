package org.codehaus.xsite;

import java.io.File;

import junit.framework.TestCase;

/**
 * @author Mauro Talevi
 */
public abstract class AbstractXSiteTestCase extends TestCase {
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
    
}
