package ru.organizilla.html;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class HtmlReader {
    public String readHtmlFromFile(String filePath) throws IOException {
        ClassPathResource resource = new ClassPathResource(filePath);
        byte[] htmlContent = FileCopyUtils.copyToByteArray((resource).getInputStream());
        return new String(htmlContent, StandardCharsets.UTF_8);
    }
}
