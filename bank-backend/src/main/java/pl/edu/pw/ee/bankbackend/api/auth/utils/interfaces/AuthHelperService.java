package pl.edu.pw.ee.bankbackend.api.auth.utils.interfaces;


import pl.edu.pw.ee.bankbackend.api.auth.data.LoginRequest;
import pl.edu.pw.ee.bankbackend.api.auth.data.RegisterRequest;
import pl.edu.pw.ee.bankbackend.api.auth.data.ResetPasswordRequest;
import pl.edu.pw.ee.bankbackend.entities.attempts.LoginAttempt;
import pl.edu.pw.ee.bankbackend.entities.user.User;

public interface AuthHelperService {
    User buildRequestIntoUser(RegisterRequest registerRequest, LoginAttempt loginAttempt);

    void executeUserAuthenticationProcess(LoginAttempt loginAttempt, LoginRequest loginRequest);

    void executePasswordResetProcess(ResetPasswordRequest request, User user);

    void savePasswordResetToken(String passwordRefreshToken, User user);
}
