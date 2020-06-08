package com.sarief;

import java.io.*;

public class Application {

    static void RecursivePrint(File[] arr, int index, int level) {
        // terminate condition
        if (index == arr.length) {
            return;
        }

        // tabs for internal levels
        for (int i = 0; i < level; i++) {
            System.out.print("\t");
        }

        // for files
        if (arr[index].isFile()) {
            File file = arr[index];
            System.out.println(getFileNameAndCodeLines(file));
        } else if (arr[index].isDirectory()) { // for sub-directories
            System.out.println("[" + arr[index].getName() + "]");

            // recursion for sub-directories
            RecursivePrint(arr[index].listFiles(), 0, level + 1);
        }

        // recursion for main directory
        RecursivePrint(arr, ++index, level);
    }

    private static String countLines(File file) {
        try {
//            return countLinesInner(file);
            return countCodeLines(file);
        } catch (IOException e) {
            return "N/A";
        }

    }

    private static String countLinesFast(File file) throws IOException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        try {
            byte[] c = new byte[1024];

            int readChars = inputStream.read(c);
            if (readChars == -1) {
                // nothing to read
                return "0";
            }

            // optimiser loop https://stackoverflow.com/a/453067/5070158
            int count = 0;
            while (readChars == 1024) {
                for (int i = 0; i < 1024; ) {
                    if (c[i++] == '\n') {
                        count++;
                    }
                }
                readChars = inputStream.read(c);
            }

            // count remaining characters
            while (readChars != -1) {
                // System.out.println(readChars); // debug point
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        count++;
                    }
                }
                readChars = inputStream.read(c);
            }

            return count == 0 ? "1" : Integer.toString(count);
        } finally {
            inputStream.close();
        }
    }

    private static String countCodeLines(File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
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
                if(chars[0] == '"'){ // not sure how valid such .java file, but just in case
                    stringLiteralOpen = !stringLiteralOpen;
                    increase = true;
                }

                if(length == 1){
                    count++;
                    continue; // end of line, next line
                }
            }

            if (!blockCommentOpen && chars[0] == '/' && chars[1] == '/') {
                continue; // whole line is commented out, next line
            }

            for (int i = 1; i < length; i++) { // start with 2nd element
                if(blockCommentOpen){
                    if (chars[i] == '/' && chars[i - 1] == '*') {
                        blockCommentOpen = false;
                    }
                    continue;
                }
                if (!stringLiteralOpen && chars[i] == '/' && chars[i - 1] == '/') {
                    break;
                }
                if (chars[i] == '"' && chars[i-1] != '\\' && chars[i-1] != '\'') {
                    stringLiteralOpen = !stringLiteralOpen;
                    increase =true;
                    continue;
                }
                if (!stringLiteralOpen && chars[i] == '*' && chars[i - 1] == '/') { // we start block comment
                    blockCommentOpen = true;
                    i++; //  avoid /*/
                    continue;
                }
                increase =true;

            }
            if (increase) {
                count++;
            }
        }
        return Integer.toString(count);
    }

    // Driver Method
    public static void main(String[] args) throws IOException {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        String path = reader.readLine();
//        System.out.println("Input is: " + path);

//        String path = "D:\\leetcode\\speedrun\\src";
        String path = "D:\\leetcode\\speedrun\\src\\ste\\test.java";
        File mainDir = new File(path);

        if (mainDir.exists() && mainDir.isDirectory()) {

            RecursivePrint(mainDir.listFiles(), 0, 0); // cannot be null, we check with mainDir.exists()
        } else if (mainDir.exists() && !mainDir.isDirectory()) {
            System.out.println(getFileNameAndCodeLines(mainDir));
        } else {
            System.out.println("Error: \"" + path + "\" dir or file does not exist");
        }
    }

    private static String getFileNameAndCodeLines(File file) {
        return file.getName() + " : " + countLines(file);
    }
}
