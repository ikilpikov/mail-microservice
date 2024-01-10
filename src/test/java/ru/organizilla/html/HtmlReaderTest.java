package ru.organizilla.html;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.CleanupMode;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HtmlReaderTest {
    @TempDir(cleanup = CleanupMode.ALWAYS)
    Path tempDirectory;
    HtmlReader htmlReader;

    @BeforeEach
    void setUp() throws IOException {
        Path tempFile = tempDirectory.resolve("test.html");
        Files.write(tempFile, List.of("<span th:text=${code}></span>"));

        htmlReader = new HtmlReader();
    }

    @Test
    void readHtmlFromFile() throws IOException {
        String expected = "<span th:text=${code}></span>";

        String actual = htmlReader.readHtmlFromFile(tempDirectory.toString());
        assertEquals(expected, actual);
    }
}