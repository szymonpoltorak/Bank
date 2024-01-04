package pl.edu.pw.ee.bankbackend.api.auth.interfaces;

import pl.edu.pw.ee.bankbackend.api.auth.data.AuthResponse;
import pl.edu.pw.ee.bankbackend.api.auth.data.LoginRequest;
import pl.edu.pw.ee.bankbackend.api.auth.data.RegisterRequest;
import pl.edu.pw.ee.bankbackend.api.auth.data.SimpleStringResponse;

public interface AuthService {
    void register(RegisterRequest userRequest, String userAgent);

    AuthResponse login(LoginRequest loginRequest, String userAgent);

    AuthResponse refreshToken(String refreshToken);

    SimpleStringResponse getPasswordCombinationForUser(String username);
}
