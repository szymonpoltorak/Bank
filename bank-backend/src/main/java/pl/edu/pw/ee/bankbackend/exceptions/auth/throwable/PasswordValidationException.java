package pl.edu.pw.ee.bankbackend.exceptions.auth.throwable;

import jakarta.validation.ValidationException;

public class PasswordValidationException extends ValidationException {
    public PasswordValidationException(String message) {
        super(message);
    }
}
