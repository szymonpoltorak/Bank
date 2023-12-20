package pl.edu.pw.ee.bankbackend.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.bankbackend.api.auth.data.AuthResponse;
import pl.edu.pw.ee.bankbackend.config.constants.TokenRevokeStatus;
import pl.edu.pw.ee.bankbackend.config.jwt.interfaces.JwtService;
import pl.edu.pw.ee.bankbackend.config.jwt.interfaces.TokenManagerService;
import pl.edu.pw.ee.bankbackend.entities.token.JwtToken;
import pl.edu.pw.ee.bankbackend.entities.token.TokenType;
import pl.edu.pw.ee.bankbackend.entities.token.interfaces.TokenRepository;
import pl.edu.pw.ee.bankbackend.entities.user.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenManagerServiceImpl implements TokenManagerService {
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    @Override
    public final void saveUsersToken(String jwtToken, User user) {
        tokenRepository.save(buildToken(jwtToken, user));
    }

    @Override
    public final AuthResponse buildTokensIntoResponse(String authToken, String refreshToken) {
        return buildResponse(authToken, refreshToken);
    }

    @Override
    public final AuthResponse buildTokensIntoResponse(User user, TokenRevokeStatus shouldBeRevoked) {
        String authToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        if (shouldBeRevoked == TokenRevokeStatus.TO_REVOKE) {
            revokeUserTokens(user);
        }
        saveUsersToken(authToken, user);

        return buildResponse(authToken, refreshToken);
    }

    @Override
    public final void revokeUserTokens(User user) {
        List<JwtToken> userTokens = tokenRepository.findAllValidTokensByUserId(user.getUserId());

        if (userTokens.isEmpty()) {
            return;
        }

        userTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenRepository.saveAll(userTokens);
    }

    private AuthResponse buildResponse(String authToken, String refreshToken) {
        return AuthResponse
                .builder()
                .authToken(authToken)
                .refreshToken(refreshToken)
                .build();
    }

    private JwtToken buildToken(String jwtToken, User user) {
        return JwtToken
                .builder()
                .token(jwtToken)
                .tokenType(TokenType.JWT_BEARER_TOKEN)
                .user(user)
                .build();
    }
}
