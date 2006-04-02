package org.codehaus.xsite.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.xsite.Main;

/**
 * Mojo to run XSite via Main CLI
 * 
 * @author Mauro Talevi
 * @goal run
 * @todo Find a way to inject Mojo with params without the need to make them protected
 */
public class XSiteRunMojo  extends AbstractMojo {
    
    /**
     * @parameter
     * @required true
     */
    protected String siteMapPath;
    
    /**
     * @parameter
     * @required true
     */
    protected String skinPath;

    /**
     * @parameter
     * @required true
     */
    protected String outputDirectoryPath;
    
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            getLog().debug("Executing XSite run goal with siteMapPath=" + siteMapPath + ", skinPath=" + skinPath + ", outputDirectoryPath=" + outputDirectoryPath);
            Main.main(new String[] {siteMapPath, skinPath, outputDirectoryPath});
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to run xsite", e);
        }
    }

}
