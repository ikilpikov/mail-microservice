package ru.organizilla.html;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class HtmlHandlerTest {
    @Mock
    private HtmlReader htmlReader;
    String htmlTemplate;
    @InjectMocks
    HtmlHandler htmlHandler;

    @BeforeEach
    void setUp() throws IOException {
        htmlTemplate = "<span th:text=${code}></span>";

        MockitoAnnotations.openMocks(this);
        when(htmlReader.readHtmlFromFile("mockedPath")).thenReturn(htmlTemplate);
        htmlHandler.initializeHtmlContent("mockedPath");
    }

    @Test
    void getHtmlContent() {
        String expected = "<span th:text=${code}></span>";
        assertEquals(expected, htmlTemplate);
    }

    @Test
    void setVariableValue() {
        String expected = "<span th:text=6></span>";

        htmlHandler.setVariableValue("code", "6");
        String actual = htmlHandler.getHtmlContent();

        assertEquals(expected, actual);
    }
}