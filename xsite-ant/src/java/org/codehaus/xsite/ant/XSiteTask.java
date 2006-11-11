package org.codehaus.xsite.ant;

import java.util.ArrayList;
import java.util.Arrays;
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

    private String siteMapPath;
    
    private String skinPath;

    private String outputDirectoryPath;

    private String compositionFilePath;
    
    private String compositionResourcePath;

    public void execute() throws BuildException {
        String[] args = getArgs();            
        try {
            Main.main(args);
        } catch (Exception e) {
            throw new BuildException("Failed to run xsite with args "+Arrays.toString(args), e);
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

    public void setCompositionFilePath(String compositionFilePath) {
        this.compositionFilePath = compositionFilePath;
    }

    public void setCompositionResourcePath(String compositionResourcePath) {
        this.compositionResourcePath = compositionResourcePath;
    }

    public void setOutputDirectoryPath(String outputDirectoryPath) {
        this.outputDirectoryPath = outputDirectoryPath;
    }

    public void setSiteMapPath(String siteMapPath) {
        this.siteMapPath = siteMapPath;
    }

    public void setSkinPath(String skinPath) {
        this.skinPath = skinPath;
    }

    
    
}
