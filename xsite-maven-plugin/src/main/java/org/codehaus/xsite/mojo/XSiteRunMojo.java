package org.codehaus.xsite.mojo;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.xsite.Main;

/**
 * Mojo to run XSite via Main CLI
 * 
 * @author Mauro Talevi
 * @goal run
 */
public class XSiteRunMojo  extends AbstractMojo {
    
    /**
     * @parameter
     * @required true
     */
    String sourceDirectoryPath;

    /**
     * @parameter
     * @required true
     */
    String sitemapPath;
    
    /**
     * @parameter
     * @required true
     */
    String skinPath;

    /**
     * @parameter
     * @required true
     */
    String outputDirectoryPath;

    /**
     * @parameter
     */
    String resourcePaths;
 
    /**
     * @parameter
     */
    String templatesDirectoryPath;

    /**
     * @parameter
     */
    String localisations;

    /**
     * @parameter
     */
    String compositionFilePath;
    
    /**
     * @parameter
     */
    String compositionResourcePath;

    /**
     * @parameter
     */
    String publishedDate;

    /**
     * @parameter
     */
    String publishedVersion;

    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            List<String> args = cliArgs();
            getLog().debug("Executing XSite run goal with args "+args);
            Main.main(args.toArray(new String[args.size()]));
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to run xsite", e);
        }
    }

    protected List<String> cliArgs() {
        List<String> args = new ArrayList<String>();
        args.add("-S" + sourceDirectoryPath);
        args.add("-m" + sitemapPath);
        args.add("-s" + skinPath);
        args.add("-o" + outputDirectoryPath);
        if (resourcePaths != null) {
            args.add("-R" + resourcePaths);
        }
        if (templatesDirectoryPath != null) {
            args.add("-T" + templatesDirectoryPath);
        }
        if (localisations != null) {
            args.add("-L" + localisations);
        }
        if (compositionFilePath != null) {
            args.add("-f" + compositionFilePath);
        }
        if (compositionResourcePath != null) {
            args.add("-r" + compositionResourcePath);
        }
        if (publishedDate !=null ){
        	args.add("-D" +publishedDate);
        }
        if (publishedVersion!=null ){
        	args.add("-V" +publishedVersion);
        }
        return args;
    }

}
