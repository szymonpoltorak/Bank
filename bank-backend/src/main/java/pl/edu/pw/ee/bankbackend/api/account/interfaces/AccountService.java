package pl.edu.pw.ee.bankbackend.api.account.interfaces;

import pl.edu.pw.ee.bankbackend.api.account.data.AccountResponse;
import pl.edu.pw.ee.bankbackend.api.auth.data.RegisterRequest;
import pl.edu.pw.ee.bankbackend.entities.user.User;

public interface AccountService {
    AccountResponse getAccountDetails(User user);

    void createNewAccount(RegisterRequest request, User user);
}
