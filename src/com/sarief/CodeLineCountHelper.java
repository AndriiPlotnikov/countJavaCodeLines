package com.sarief;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Code line counter helper
 */
public class CodeLineCountHelper {

    private static Map<String, Integer> cache = new HashMap<>();

    private CodeLineCountHelper(){}

    /**
     * count code lines in file
     * <p>
     * ignores empty lines, commented out lines
     * properly processes cases with string literals
     * might not properly process not compilable source files
     *
     * @param file file to count code liens
     * @return code lines
     * @throws IOException if error occurred during file reading
     */
    public static int countCodeLines(File file) throws IOException {
        if (cache.containsKey(file.getAbsolutePath())) {
            return cache.get(file.getAbsolutePath());
        }

        // TODO reading by line may not be best approach when working with big files, but java sources should never be
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            int count = 0;
            boolean stringLiteralOpen = false;
            boolean blockCommentOpen = false;
            boolean increase;

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                char[] chars = line.trim().toCharArray();
                int length = chars.length;
                if (length == 0) {
                    continue; // nothing to do, next line
                }
                increase = false;

                if (!blockCommentOpen) {
                    if (chars[0] == '"') { // not sure how valid such .java file, but just in case
                        stringLiteralOpen = !stringLiteralOpen;
                        increase = true;
                    }

                    if (length == 1) {
                        count++;
                        continue; // end of line, next line
                    }
                }

                if (!blockCommentOpen && chars[0] == '/' && chars[1] == '/') {
                    continue; // whole line is commented out, next line
                }

                for (int i = 1; i < length; i++) { // start with 2nd element
                    if (blockCommentOpen) {
                        if (chars[i] == '/' && chars[i - 1] == '*') {
                            blockCommentOpen = false;
                        }
                        continue;
                    }
                    if (!stringLiteralOpen && chars[i] == '/' && chars[i - 1] == '/') {
                        break;
                    }
                    if (chars[i] == '"' && chars[i - 1] != '\\' && chars[i - 1] != '\'') {  // + avoid chars and \"
                        stringLiteralOpen = !stringLiteralOpen;
                        increase = true;
                        continue;
                    }
                    if (!stringLiteralOpen && chars[i] == '*' && chars[i - 1] == '/') { // we start block comment
                        blockCommentOpen = true;
                        i++; //  avoid /*/ situation
                        continue;
                    }
                    increase = true;

                }
                if (increase) {
                    count++;
                }
            }
            cache.put(file.getAbsolutePath(), count);
            return count;
        }
    }
}
