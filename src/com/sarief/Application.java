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

        File dirOrFile = new File(path);

        if (dirOrFile.exists()) {
            if (dirOrFile.isDirectory()) {
                System.out.println(dirOrFile.getName() + " : " + FileHelper.recursiveCalc(dirOrFile.listFiles(), 0));
                FileHelper.recursivePrintv2(dirOrFile.listFiles(), 0, 1);// ignore warning, cannot be null if isDirectory
            } else {
                System.out.println(dirOrFile.getName()+ " : " + CodeLineCountHelper.countCodeLines(dirOrFile));
            }
        } else {
            System.out.println("Error: \"" + path + "\" dir or file does not exist");
        }
    }

}
