package ru.organizilla.html;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HtmlHandler {
    private final HtmlReader htmlReader;
    private StringBuilder htmlContent;

    @Autowired
    public HtmlHandler(HtmlReader htmlReader) {
        this.htmlReader = htmlReader;
    }

    public String getHtmlContent() {
        return htmlContent.toString();
    }

    public void initializeHtmlContent(String documentAllocation) throws IOException {
        String content = htmlReader.readHtmlFromFile(documentAllocation);
        htmlContent = new StringBuilder(content);
    }

    public void setVariableValue(String variable, String value) {
        String pattern = "${" + variable + "}";
        replace(htmlContent, pattern, value);
    }

    private void replace(StringBuilder sb, String target, String replacement) {
        int index = sb.indexOf(target);
        while (index != -1) {
            sb.replace(index, index + target.length(), replacement);
            index = sb.indexOf(target, index + replacement.length());
        }
    }

}
