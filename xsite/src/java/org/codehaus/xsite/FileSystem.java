package org.codehaus.xsite;

import java.io.File;

/**
 * Handles filesystem operations
 * 
 * @author Mauro Talevi
 */
public interface FileSystem {

    String readFile(File file);

    void copyFile(File source, File destination);

    void copyDirectory(File sourceDirectory, File targetDirectory, boolean recurse);

}
