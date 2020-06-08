package com.sarief;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class CodeLineCountHelperTest {

    @Test
    public void countLines_oddFile_countsProperly() {
        String expected = Integer.toString(16);
        File file = new File("resources\\test.java");
        String lines = CodeLineCountHelper.countLines(file);

        Assert.assertEquals(expected, lines);
    }

    @Test
    public void countLines_commentedLines_noLineCounted() {
        String expected = Integer.toString(0);
        File file = new File("resources\\commented.java");
        String lines = CodeLineCountHelper.countLines(file);

        Assert.assertEquals(expected, lines);
    }

    @Test
    public void countLines_emptyFile_noLineCounted() {
        String expected = Integer.toString(0);
        File file = new File("resources\\empty.java");
        String lines = CodeLineCountHelper.countLines(file);

        Assert.assertEquals(expected, lines);
    }


    @Test
    public void countLines_whitespaceFile_noLineCounted() {
        String expected = Integer.toString(0);
        File file = new File("resources\\whitespace.java");
        String lines = CodeLineCountHelper.countLines(file);

        Assert.assertEquals(expected, lines);
    }

    @Test
    public void countLines_oneCharacterLinesFile_allLinesCounted() {
        String expected = Integer.toString(8);
        File file = new File("resources\\oneCharLine.java");
        String lines = CodeLineCountHelper.countLines(file);

        Assert.assertEquals(expected, lines);
    }
}
