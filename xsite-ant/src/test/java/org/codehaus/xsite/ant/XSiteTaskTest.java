package org.codehaus.xsite.ant;

import java.io.File;

import junit.framework.TestCase;

import org.apache.tools.ant.BuildException;

/**
 * 
 * @author Mauro Talevi
 */
public class XSiteTaskTest extends TestCase {
    protected String testSrcDir;

    public void setUp() throws Exception {
        setTestDir();
    }

    protected void setTestDir() {
        testSrcDir = System.getProperty("test.src.dir");
        if (testSrcDir == null) {
            testSrcDir = "xsite-ant/src/test/";
        } else if (!testSrcDir.endsWith(File.separator)) {
            testSrcDir = testSrcDir + File.separator;
        }
    }

    public void testGoalCanBeRunWithDefaultComposition() throws Exception {
        XSiteTask task = new XSiteTask();
        task.setSourceDirectoryPath(testSrcDir);
        task.setSitemapPath("content/sitemap.xml");
        task.setSkinPath("templates/skin.html");
        task.setOutputDirectoryPath("target/xsite");
        task.execute();
    }
    
    public void testGoalCanBeRunWithOptionalResources() throws Exception {
        XSiteTask task = new XSiteTask();
        task.setSourceDirectoryPath(testSrcDir);
        task.setSitemapPath("content/sitemap.xml");
        task.setSkinPath("templates/skin.html");
        task.setResourcePaths("resources");
        task.setOutputDirectoryPath("target/xsite");
        task.execute();
    }

    public void testGoalCanBeRunWithOptionalFileComposition() throws Exception {
        XSiteTask task = new XSiteTask();
        task.setSourceDirectoryPath(testSrcDir);
        task.setSitemapPath("content/sitemap.xml");
        task.setSkinPath("templates/skin.html");
        task.setCompositionFilePath("templates/xsite.xml");
        task.setOutputDirectoryPath("target/xsite");
        task.execute();
    }

    public void testGoalCanBeRunWithOptionalResourceComposition()
            throws Exception {
        XSiteTask task = new XSiteTask();
        task.setSourceDirectoryPath(testSrcDir);
        task.setSitemapPath("content/sitemap.xml");
        task.setSkinPath("templates/skin.html");
        task.setCompositionResourcePath("templates/xsite.xml");
        task.setOutputDirectoryPath("target/xsite");
        task.execute();
    }

    public void testGoalFailsWithInvalidPath() throws Exception {
        XSiteTask task = new XSiteTask();
        task.setSourceDirectoryPath(testSrcDir);
        task.setSitemapPath("content/nonexistent.xml");
        task.setSkinPath("templates/skin.html");
        task.setOutputDirectoryPath("target/xsite");
        try {
            task.execute();
            fail("taskExecutionException expected");
        } catch (BuildException e) {
            // expected
        }
    }
}
