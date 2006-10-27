package org.codehaus.xsite.mojo;

import java.util.ArrayList;
import java.util.Arrays;
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
    String siteMapPath;
    
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
    String compositionFilePath;
    
    /**
     * @parameter
     */
    protected String compositionResourcePath;

    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            String[] args = getArgs();
            getLog().debug("Executing XSite run goal with args "+Arrays.toString(args));
            Main.main(args);
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to run xsite", e);
        }
    }

    private String[] getArgs() {
        List<String> args = new ArrayList<String>();
        args.add("-m" + siteMapPath);
        args.add("-s" + skinPath);
        args.add("-o" + outputDirectoryPath);
        if (compositionFilePath != null) {
            args.add("-f" + compositionFilePath);
        }
        if (compositionResourcePath != null) {
            args.add("-r" + compositionResourcePath);
        }
        return (String[]) args.toArray(new String[args.size()]);
    }

}
