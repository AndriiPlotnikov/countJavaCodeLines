package com.sarief;

import java.io.File;

/**
 * Helper for file operations
 */
public class FileHelper {

    private FileHelper(){}

    public static final String TAB_CHARACTER = "\t";

    /**
     * recursively print directory structure
     * if file is in, print file name with number of code lines in said file
     *
     * @param files list of files in directory
     * @param index index of file to start with
     * @param level depth of recursion (for tabs)
     */
    public static void recursivePrint(File[] files, int index, int level) {
        // terminate condition
        if (index == files.length) {
            return;
        }

        // tabs for internal levels
        for (int i = 0; i < level; i++) {
            System.out.print(TAB_CHARACTER);
        }

        // for files
        File file = files[index];
        if (files[index].isFile()) {
            System.out.println(getFileNameAndCodeLines(file));
        } else if (files[index].isDirectory()) { // for sub-directories
            System.out.println("[" + file.getName() + "]");

            // recursion for sub-directories
            // ignore warning, cannot be null if isDirectory()
            recursivePrint(file.listFiles(), 0, level + 1);
        }

        // recursion for main directory
        recursivePrint(files, ++index, level);
    }


    /**
     * extract file name and number of code lines
     *
     * {@see CodeLineCountHelper}
     *
     * @param file file to extract name & code lines from
     * @return name and code lines
     */
    public static String getFileNameAndCodeLines(File file) {
        return file.getName() + " : " + CodeLineCountHelper.countLines(file);
    }
}
