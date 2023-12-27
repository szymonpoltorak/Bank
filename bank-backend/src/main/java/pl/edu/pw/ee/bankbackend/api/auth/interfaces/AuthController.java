package pl.edu.pw.ee.bankbackend.api.auth.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import pl.edu.pw.ee.bankbackend.api.auth.data.AuthResponse;
import pl.edu.pw.ee.bankbackend.api.auth.data.LoginRequest;
import pl.edu.pw.ee.bankbackend.api.auth.data.RegisterRequest;
import pl.edu.pw.ee.bankbackend.api.auth.data.ResetPasswordRequest;
import pl.edu.pw.ee.bankbackend.api.auth.data.SimpleStringResponse;

public interface AuthController {
    AuthResponse registerUser(RegisterRequest registerRequest, HttpServletRequest request);

    AuthResponse loginUser(LoginRequest loginRequest, HttpServletRequest request);

    AuthResponse refreshUserToken(String refreshToken);

    SimpleStringResponse requestResetUsersPassword(String username);

    SimpleStringResponse resetUsersPassword(ResetPasswordRequest request);

    SimpleStringResponse getPasswordCombinationForUser(String username);
}
