package pl.edu.pw.ee.bankbackend.config.jwt.interfaces;


import pl.edu.pw.ee.bankbackend.api.auth.data.AuthResponse;
import pl.edu.pw.ee.bankbackend.config.constants.TokenRevokeStatus;
import pl.edu.pw.ee.bankbackend.entities.user.User;

public interface TokenManagerService {
    AuthResponse buildTokensIntoResponse(String authToken, String refreshToken);

    AuthResponse buildTokensIntoResponse(User user, TokenRevokeStatus shouldBeRevoked);

    void revokeUserTokens(User user);

    void saveUsersToken(String jwtToken, User user);
}
