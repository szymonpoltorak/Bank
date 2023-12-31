package pl.edu.pw.ee.bankbackend.api.auth.interfaces;

import pl.edu.pw.ee.bankbackend.api.auth.data.AuthResponse;
import pl.edu.pw.ee.bankbackend.api.auth.data.LoginRequest;
import pl.edu.pw.ee.bankbackend.api.auth.data.ResetPasswordRequest;
import pl.edu.pw.ee.bankbackend.api.auth.data.SimpleStringResponse;

public interface AuthController {
    AuthResponse loginUser(LoginRequest loginRequest, String userAgent);

    AuthResponse refreshUserToken(String refreshToken);

    SimpleStringResponse requestResetUsersPassword(String username);

    SimpleStringResponse resetUsersPassword(ResetPasswordRequest request);

    SimpleStringResponse getPasswordCombinationForUser(String username);
}
