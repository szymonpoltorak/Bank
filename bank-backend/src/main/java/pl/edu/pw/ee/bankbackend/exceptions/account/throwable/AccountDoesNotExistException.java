package pl.edu.pw.ee.bankbackend.exceptions.account.throwable;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class AccountDoesNotExistException extends ResponseStatusException {
    public AccountDoesNotExistException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}
