package hr.fer.zemris.java.hw12.jvdraw.actions;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * Class representing file filter in file chooser.
 * @author Juraj
 *
 */

public class FileTypeFilter extends FileFilter {
	
	/**
	 * File extension
	 */
    private String extension;
    /**
     * Extension's descpription
     */
    private String description;
 
    /**
     * Constructs file filter from given extension and description
     * @param extension given extension
     * @param description given description
     */
    public FileTypeFilter(String extension, String description) {
        this.extension = extension;
        this.description = description;
    }
 
    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        return file.getName().endsWith(extension);
    }
 
    @Override
    public String getDescription() {
        return description + String.format(" (*%s)", extension);
    }
    
    @Override
    public String toString() {
    	
    	return extension;
    }
}