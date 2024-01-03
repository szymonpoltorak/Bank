package pl.edu.pw.ee.bankbackend.api.account.interfaces;

import pl.edu.pw.ee.bankbackend.api.account.data.AccountResponse;
import pl.edu.pw.ee.bankbackend.entities.user.User;

@FunctionalInterface
public interface AccountController {
    AccountResponse getAccountDetails(User user);
}
