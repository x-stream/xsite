package org.codehaus.xsite.ant;

import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.codehaus.xsite.Main;

/**
 * Ant task that runs XSite via Main CLI
 * 
 * @author Mauro Talevi
 */
public class XSiteTask extends Task {

    private String sourceDirectoryPath;

    private String sitemapPath;
    
    private String skinPath;

    private String resourcePaths;

    private String outputDirectoryPath;

    private String localisations;
    
    private String compositionFilePath;
    
    private String compositionResourcePath;

    public void execute() throws BuildException {
        List<String> args = cliArgs();            
        try {
            Main.main(args.toArray(new String[args.size()]));
        } catch (Exception e) {
            throw new BuildException("Failed to run xsite with args "+args, e);
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
        if (localisations != null) {
            args.add("-L" + localisations);
        }
        if (compositionFilePath != null) {
            args.add("-f" + compositionFilePath);
        }
        if (compositionResourcePath != null) {
            args.add("-r" + compositionResourcePath);
        }
        return args;
    }

    public void setCompositionFilePath(String compositionFilePath) {
        this.compositionFilePath = compositionFilePath;
    }

    public void setCompositionResourcePath(String compositionResourcePath) {
        this.compositionResourcePath = compositionResourcePath;
    }

    public void setLocalisations(String localisations) {
        this.localisations = localisations;
    }

    public void setOutputDirectoryPath(String outputDirectoryPath) {
        this.outputDirectoryPath = outputDirectoryPath;
    }
    
    public void setResourcePaths(String resourcePaths) {
        this.resourcePaths = resourcePaths;
    }

    public void setSitemapPath(String sitemapPath) {
        this.sitemapPath = sitemapPath;
    }

    public void setSkinPath(String skinPath) {
        this.skinPath = skinPath;
    }

    public void setSourceDirectoryPath(String sourceDirectoryPath) {
        this.sourceDirectoryPath = sourceDirectoryPath;
    }    
        
}
