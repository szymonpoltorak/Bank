package pl.edu.pw.ee.bankbackend.exceptions.account.throwable;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotEnoughMoneyException extends ResponseStatusException {
    public NotEnoughMoneyException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}
