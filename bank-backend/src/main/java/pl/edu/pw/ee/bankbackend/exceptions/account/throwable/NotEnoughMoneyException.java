package pl.edu.pw.ee.bankbackend.exceptions.account.throwable;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public class NotEnoughMoneyException extends ResponseStatusException {
    @Serial
    private static final long serialVersionUID = -85372730876735175L;

    public NotEnoughMoneyException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}
