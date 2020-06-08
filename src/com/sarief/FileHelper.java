package com.sarief;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper for file operations
 */
public class FileHelper {

    private static Map<String, Integer> cache = new HashMap<>();

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
    public static void recursivePrintv2(File[] files, int index, int level) throws IOException {
        // terminate condition
        if (index == files.length) {
            return;
        }

        // tabs for internal levels
        for (int i = 0; i < level; i++) {
            System.out.print(TAB_CHARACTER);
        }

        if (files[index].isFile()) {// for files
            File file = files[index];
            int codeLines = CodeLineCountHelper.countCodeLines(file);
            System.out.println(file.getName() + " : " + codeLines);

        } else if (files[index].isDirectory()) { // for sub-directories
            File directory = files[index]; //

            // ignore warning, cannot be null if isDirectory()
            int lines = recursiveCalc(directory.listFiles(), 0);
            System.out.println(directory.getName() + " : " + lines);
            recursivePrintv2(directory.listFiles(), 0, level + 1);
        }

        index++;
        recursivePrintv2(files, index, level);
    }

    public static int recursiveCalc(File[] files, int index) throws IOException {
        // terminate condition
        if (index == files.length) {
            return 0;
        }

        int lines = 0;
        if (files[index].isFile()) {// for files
            File file = files[index];
            lines = CodeLineCountHelper.countCodeLines(file);
        } else if (files[index].isDirectory()) { // for sub-directories
            File directory = files[index]; //
            if (cache.containsKey(directory.getAbsolutePath())) {
                lines = cache.get(directory.getAbsolutePath());
            } else {
                // ignore warning, cannot be null if isDirectory()
                lines = recursiveCalc(directory.listFiles(), 0);
                cache.put(directory.getAbsolutePath(), lines);
            }
        }

        index++;
        return lines + recursiveCalc(files, index);
    }
}
