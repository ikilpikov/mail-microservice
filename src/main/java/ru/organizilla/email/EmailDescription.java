package ru.organizilla.email;

public record EmailDescription(
        String recipientAddress,
        String secretCode,
        String subject,
        String htmlTemplate

) {
}
