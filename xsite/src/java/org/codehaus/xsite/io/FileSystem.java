package org.codehaus.xsite.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * Facade for underlying filesystem.
 *
 * @author Joe Walnes
 */
public class FileSystem {

    public char[] readFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            try {
                char[] data = new char[(int) file.length()];
                reader.read(data);
                return data;
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            throw new FileSystemException("Cannot read data from " + file.getName(), e);
        }
    }

    public void copyDirectory(File sourceDirectory, File targetDirectory) {
        try {
            FileUtils.copyDirectory(sourceDirectory, targetDirectory);
        } catch ( IOException e) {
            throw new FileSystemException("Cannot copy " + sourceDirectory.getName() + " to " + targetDirectory.getName(), e);
        }        
    }
    
    public void copyAllFiles(File sourceDirectory, File targetDirectory, String suffixesToExclude) {
        String[] badSuffixes = suffixesToExclude.split(",");
        File[] files = sourceDirectory.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            boolean fileShouldBeCopied = file.isFile();
            for (int j = 0; j < badSuffixes.length; j++) {
                String badSuffix = badSuffixes[j];
                if (file.getName().endsWith("." + badSuffix)) {
                    fileShouldBeCopied = false;
                }
            }
            if (fileShouldBeCopied) {
                copyFile(file, new File(targetDirectory, file.getName()));
            }
        }
    }

    private void copyFile(File source, File destination) {
        try {
            FileUtils.copyFile(source, destination);
        } catch (IOException e) {
            throw new FileSystemException("Cannot copy " + source.getName() + " to " + destination.getName(), e);
        }
    }

    public static class FileSystemException extends RuntimeException {
        public FileSystemException(String message, Throwable throwable) {
            super(message, throwable);
        }
    }
}
