package ru.organizilla.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender sender;

    @Autowired
    public MailService(JavaMailSender sender) {
        this.sender = sender;
    }

    public void sendHtmlEmail(String recipientAddress, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();

        message.setSubject(subject);
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(message, true);
        helper.setTo(recipientAddress);

        helper.setText(htmlContent, true);
        sender.send(message);
    }
}
