package org.codehaus.xsite.io;

import static org.apache.commons.io.FileUtils.copyFileToDirectory;
import static org.apache.commons.io.FileUtils.listFiles;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.apache.commons.io.filefilter.FileFilterUtils.makeDirectoryOnly;
import static org.apache.commons.io.filefilter.FileFilterUtils.makeFileOnly;
import static org.apache.commons.lang.StringUtils.difference;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
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
            return readFileToString(file);
        } catch (Exception e) {
            throw new FileSystemException("Cannot read content from file " + file, e);
        }
    }

    public void copyFile(File source, File destination) {
        try {
            copyFile(source, destination);
        } catch (Exception e) {
            throw new FileSystemException("Failed to copy file " + source + " to " + destination, e);
        }
    }

    public void copyDirectory(File sourceDirectory, File targetDirectory, boolean recurse) {
        if ( !sourceDirectory.isDirectory() ){
            throw new FileSystemException("Source must be a directory "+sourceDirectory, null);
        }
        try {
            Collection<File> relativeFiles = filterRelativeFiles(sourceDirectory, getFileFilter(), recurse);
            for (File relativeFile : relativeFiles) {
                File sourceFile = new File(sourceDirectory, relativeFile.getPath());
                File relativeDirectory = new File(targetDirectory, relativeFile.getParent());
                copyFileToDirectory(sourceFile, relativeDirectory);
            }
        } catch (Exception e) {
            throw new FileSystemException("Failed to copy directory " + sourceDirectory + " to " + targetDirectory, e);
        }
    }

    @SuppressWarnings("unchecked")
    private Collection<File> filterRelativeFiles(File sourceDirectory, IOFileFilter filter, boolean recurse) {
        Collection<File> files = listFiles(sourceDirectory, makeFileOnly(filter),
                (recurse ? makeDirectoryOnly(filter) : null));
        Collection<File> relativeFiles = new ArrayList<File>();
        String sourceDirectoryPath = sourceDirectory.getPath();
        for (File file : files) {
            String filePath = file.getPath();
            String relativePath = difference(sourceDirectoryPath, filePath);
            relativeFiles.add(new File(relativePath));
        }
        return relativeFiles;
    }

    /**
     * Specifies the file filter used in the #copyDirectory() method.
     * 
     * @return An IOFileFilter
     */
    protected IOFileFilter getFileFilter() {
        return SVN_AWARE_FILTER;
    }

    @SuppressWarnings("serial")
    public static class FileSystemException extends RuntimeException {
        public FileSystemException(String message, Throwable throwable) {
            super(message, throwable);
        }
    }
}
