package com.sarief;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class CodeLineCountHelperTest {

    @Test
    public void countLines_oddFile_countsProperly() throws IOException {
        int expected = 16;
        File file = new File("resources\\tests2\\test.java");
        int lines = CodeLineCountHelper.countCodeLines(file);

        Assert.assertEquals(expected, lines);
    }

    @Test
    public void countLines_commentedLines_noLineCounted() throws IOException {
        int expected = 0;
        File file = new File("resources\\tests\\commented.java");
        int lines = CodeLineCountHelper.countCodeLines(file);

        Assert.assertEquals(expected, lines);
    }

    @Test
    public void countLines_emptyFile_noLineCounted() throws IOException {
        int expected = 0;
        File file = new File("resources\\tests\\inner\\empty.java");
        int lines = CodeLineCountHelper.countCodeLines(file);

        Assert.assertEquals(expected, lines);
    }


    @Test
    public void countLines_whitespaceFile_noLineCounted() throws IOException {
        int expected = 0;
        File file = new File("resources\\tests\\whitespace.java");
        int lines = CodeLineCountHelper.countCodeLines(file);

        Assert.assertEquals(expected, lines);
    }

    @Test
    public void countLines_oneCharacterLinesFile_allLinesCounted() throws IOException {
        int expected = 8;
        File file = new File("resources\\tests\\oneCharLine.java");
        int lines = CodeLineCountHelper.countCodeLines(file);

        Assert.assertEquals(expected, lines);
    }
}
