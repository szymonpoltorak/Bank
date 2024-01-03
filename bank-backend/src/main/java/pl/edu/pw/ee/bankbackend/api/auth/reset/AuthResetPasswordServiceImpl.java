package pl.edu.pw.ee.bankbackend.api.auth.reset;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.bankbackend.api.auth.data.ResetPasswordRequest;
import pl.edu.pw.ee.bankbackend.api.auth.data.SimpleStringResponse;
import pl.edu.pw.ee.bankbackend.api.auth.reset.interfaces.AuthResetPasswordService;
import pl.edu.pw.ee.bankbackend.api.auth.utils.interfaces.PasswordCombinationService;
import pl.edu.pw.ee.bankbackend.config.constants.Properties;
import pl.edu.pw.ee.bankbackend.config.jwt.interfaces.JwtService;
import pl.edu.pw.ee.bankbackend.entities.password.interfaces.PasswordCombinationRepository;
import pl.edu.pw.ee.bankbackend.entities.token.JwtToken;
import pl.edu.pw.ee.bankbackend.entities.token.TokenType;
import pl.edu.pw.ee.bankbackend.entities.token.interfaces.TokenRepository;
import pl.edu.pw.ee.bankbackend.entities.user.User;
import pl.edu.pw.ee.bankbackend.entities.user.interfaces.UserRepository;
import pl.edu.pw.ee.bankbackend.exceptions.auth.throwable.TokenDoesNotExistException;

import java.util.Collections;

import static pl.edu.pw.ee.bankbackend.api.auth.AuthServiceImpl.USER_NOT_EXIST_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthResetPasswordServiceImpl implements AuthResetPasswordService {
    private static final long PASSWORD_REFRESH_TOKEN_TIME = 600_000L;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordCombinationService passwordCombinationService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordCombinationRepository passwordCombinationRepository;
    @Value(Properties.FRONTEND_URL)
    private String frontendUrl;

    @Override
    public final SimpleStringResponse requestResetUsersPassword(String username) {
        log.info("Resetting passwordCombination for user : {}", username);

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(USER_NOT_EXIST_MESSAGE)
        );
        log.info("User to gain refresh link : {}", user);

        String passwordRefreshToken = jwtService.generateToken(Collections.emptyMap(),
                user, PASSWORD_REFRESH_TOKEN_TIME);

        log.info("Password refresh link : {}/auth/resetPassword?token={}", frontendUrl, passwordRefreshToken);

        savePasswordResetToken(passwordRefreshToken, user);

        return new SimpleStringResponse(username);
    }

    @Override
    public final SimpleStringResponse resetUsersPassword(ResetPasswordRequest request) {
        log.info("Resetting passwordCombination for user with token : {}", request.resetPasswordToken());

        String username = jwtService.getClaimFromToken(request.resetPasswordToken(), Claims::getSubject)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_EXIST_MESSAGE));

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(USER_NOT_EXIST_MESSAGE)
        );
        log.info("User to reset passwordCombination : {}", user);

        User newUser = executePasswordResetProcess(request, user);

        passwordCombinationService.generateCombinationsForPassword(request.newPassword(), newUser);

        return new SimpleStringResponse(username);
    }

    private User executePasswordResetProcess(ResetPasswordRequest request, User user) {
        JwtToken jwtToken = tokenRepository.findByTokenAndUser(request.resetPasswordToken(), user)
                .orElseThrow(
                        () -> new TokenDoesNotExistException("Token does not exist!")
                );
        log.info("Token to reset passwordCombination : {}", jwtToken);

        String encodedPassword = passwordEncoder.encode(request.newPassword());

        log.info("Setting new passwordCombination : {}", encodedPassword);

        user.setPassword(encodedPassword);

        log.info("Saving new user data and deleting token : {}", jwtToken);

        passwordCombinationRepository
                .findAllByUser(user)
                .forEach(passwordCombinationRepository::deleteById);

        tokenRepository.deleteById(jwtToken.getTokenId());

        return userRepository.save(user);
    }

    private void savePasswordResetToken(String passwordRefreshToken, User user) {
        JwtToken jwtToken = JwtToken
                .builder()
                .token(passwordRefreshToken)
                .user(user)
                .tokenType(TokenType.RESET_PASSWORD_TOKEN)
                .build();
        log.info("Token to save : {}", jwtToken);

        tokenRepository.save(jwtToken);
    }
}
