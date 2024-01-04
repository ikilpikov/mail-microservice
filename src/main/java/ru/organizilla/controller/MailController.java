package ru.organizilla.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.organizilla.email.EmailDescription;
import ru.organizilla.email.EmailSubject;
import ru.organizilla.html.HtmlHandler;
import ru.organizilla.service.MailService;

import java.io.IOException;

@RestController
@RequestMapping("/email-sender")
@Validated
public class MailController {
    private final MailService mailService;
    private final HtmlHandler htmlHandler;
    private static final String REGISTRATION_EMAIL_TEMPLATE = "templates/mail.html";
    private static final String PASSWORD_CHANGE_EMAIL_TEMPLATE = "templates/mail.html";

    @Autowired
    public MailController(MailService mailService, HtmlHandler htmlHandler) {
        this.mailService = mailService;
        this.htmlHandler = htmlHandler;
    }

    @GetMapping(value = "/registration")
    public ResponseEntity<String> sendRegistrationEmail(@Email @RequestParam(name = "address") String recipientAddress,
                                               @RequestParam(name = "code") String secretCode) {
        var email = new EmailDescription(recipientAddress,
                secretCode,
                EmailSubject.REGISTRATION.getDescription(),
                REGISTRATION_EMAIL_TEMPLATE);
        return sendHtmlMail(email);
    }

    @GetMapping(value = "/change-password")
    public ResponseEntity<String> sendPasswordChangeEmail(@Email @RequestParam(name = "address") String recipientAddress,
                                                       @RequestParam(name = "code") String secretCode) {
        var email = new EmailDescription(recipientAddress,
                secretCode,
                EmailSubject.PASSWORD_CHANGE.getDescription(),
                PASSWORD_CHANGE_EMAIL_TEMPLATE);
        return sendHtmlMail(email);
    }

    private ResponseEntity<String> sendHtmlMail(EmailDescription email) {
        try {
            htmlHandler.initializeHtmlContent(email.htmlTemplate());
            htmlHandler.setVariableValue("secretCode", email.secretCode());
            String content = htmlHandler.getHtmlContent();

            mailService.sendHtmlEmail(email.recipientAddress(), email.subject(), content);
        } catch (MessagingException ex) {
            return new ResponseEntity<>("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException ex) {
            return new ResponseEntity<>("Mail content does not exist", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Please check your inbox", HttpStatus.OK);
    }
}
