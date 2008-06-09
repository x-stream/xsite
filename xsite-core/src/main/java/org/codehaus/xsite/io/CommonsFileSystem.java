package org.codehaus.xsite.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang.StringUtils;
import org.codehaus.xsite.FileSystem;

/**
 * Commons-based FileSystem implementation
 * 
 * @author Joe Walnes
 * @author Mauro Talevi
 */
public class CommonsFileSystem implements FileSystem {
    
    private static final IOFileFilter SVN_AWARE_FILTER = FileFilterUtils.makeSVNAware(null);

    public String readFile(File file) {
        try {
            return FileUtils.readFileToString(file);
        } catch (IOException e) {
            throw new FileSystemException("Cannot read data from " + file.getName(), e);
        }
    }

    public void copyFile(File source, File destination) {
        try {
            FileUtils.copyFile(source, destination);
        } catch (IOException e) {
            throw new FileSystemException("Failed to copy file " + source.getName() + " to " + destination.getName(), e);
        }
    }

    public void copyDirectory(File sourceDirectory, File targetDirectory, boolean recurse) {
        Collection relativeFiles = filterRelativeFiles(sourceDirectory, getFileFilter(), recurse);
        try {
            for (Iterator i = relativeFiles.iterator(); i.hasNext();) {
                File relativeFile = (File) i.next();
                File sourceFile = new File(sourceDirectory, relativeFile.getPath());
                File relativeDirectory = new File(targetDirectory, relativeFile.getParent());
                FileUtils.copyFileToDirectory(sourceFile, relativeDirectory);
            }
        } catch (IOException e) {
            throw new FileSystemException("Failed to copy directory " + sourceDirectory.getName() + " to "
                    + targetDirectory.getName(), e);
        }
    }

    private Collection filterRelativeFiles(File sourceDirectory, IOFileFilter filter, boolean recurse) {
        Collection files = FileUtils.listFiles(sourceDirectory, FileFilterUtils.makeFileOnly(filter),
                (recurse ? FileFilterUtils.makeDirectoryOnly(filter) : null));
        Collection relativeFiles = new ArrayList();
        String sourceDirectoryPath = sourceDirectory.getPath();
        for (Iterator i = files.iterator(); i.hasNext();) {
            String filePath = ((File) i.next()).getPath();
            String relativePath = StringUtils.difference(sourceDirectoryPath, filePath);
            relativeFiles.add(new File(relativePath));
        }
        return relativeFiles;
    }

    /**
     * Specifies the file filter used in the #copyDirectory() method.  
     * @return An IOFileFilter
     */
    protected IOFileFilter getFileFilter() {
        return SVN_AWARE_FILTER;
    }

    public static class FileSystemException extends RuntimeException {
        public FileSystemException(String message, Throwable throwable) {
            super(message, throwable);
        }
    }
}
