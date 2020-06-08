package com.sarief;

import java.io.*;

/**
 * Main class
 */
public class Application {

    /**
     * main method
     *
     * @param args console args, ignored
     * @throws IOException input output failure exception
     */
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String path = reader.readLine();
        System.out.println("Input is: " + path);

        File mainDir = new File(path);

        if (mainDir.exists()) {
            if (mainDir.isDirectory()) {
                FileHelper.recursivePrint(mainDir.listFiles(), 0, 0); // ignore warning, cannot be null if isDirectory
            } else {
                System.out.println(FileHelper.getFileNameAndCodeLines(mainDir));
            }
        } else {
            System.out.println("Error: \"" + path + "\" dir or file does not exist");
        }
    }

}
