package pl.edu.pw.ee.bankbackend.exceptions.account.throwable;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public class AccountDoesNotExistException extends ResponseStatusException {
    @Serial
    private static final long serialVersionUID = 7329845829351451350L;

    public AccountDoesNotExistException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}
