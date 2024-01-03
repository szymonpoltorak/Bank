package pl.edu.pw.ee.bankbackend.api.auth.reset.interfaces;

import pl.edu.pw.ee.bankbackend.api.auth.data.ResetPasswordRequest;
import pl.edu.pw.ee.bankbackend.api.auth.data.SimpleStringResponse;

public interface AuthResetPasswordService {
    SimpleStringResponse requestResetUsersPassword(String username);

    SimpleStringResponse resetUsersPassword(ResetPasswordRequest request);
}
