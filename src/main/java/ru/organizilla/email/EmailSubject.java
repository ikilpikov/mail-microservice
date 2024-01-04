package ru.organizilla.email;

public enum EmailSubject {
    REGISTRATION("Регистрация на Organizilla"),
    PASSWORD_CHANGE("Изменение пароля на Organizilla");

    private final String description;

    EmailSubject(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
