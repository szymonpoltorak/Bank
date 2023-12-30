package pl.edu.pw.ee.bankbackend.exceptions.account.throwable;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class AccountDoesNotExistException extends ResponseStatusException {
    public AccountDoesNotExistException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
