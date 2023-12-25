package pl.edu.pw.ee.bankbackend.api.auth.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.bankbackend.api.auth.data.LoginRequest;
import pl.edu.pw.ee.bankbackend.api.auth.data.RegisterRequest;
import pl.edu.pw.ee.bankbackend.api.auth.data.ResetPasswordRequest;
import pl.edu.pw.ee.bankbackend.api.auth.utils.interfaces.AuthHelperService;
import pl.edu.pw.ee.bankbackend.entities.attempts.LoginAttempt;
import pl.edu.pw.ee.bankbackend.entities.attempts.interfaces.LoginAttemptRepository;
import pl.edu.pw.ee.bankbackend.entities.password.PasswordOption;
import pl.edu.pw.ee.bankbackend.entities.password.interfaces.PasswordOptionRepository;
import pl.edu.pw.ee.bankbackend.entities.token.JwtToken;
import pl.edu.pw.ee.bankbackend.entities.token.TokenType;
import pl.edu.pw.ee.bankbackend.entities.token.interfaces.TokenRepository;
import pl.edu.pw.ee.bankbackend.entities.user.User;
import pl.edu.pw.ee.bankbackend.entities.user.interfaces.UserRepository;
import pl.edu.pw.ee.bankbackend.exceptions.auth.throwable.TokenDoesNotExistException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthHelperServiceImpl implements AuthHelperService {
    private static final String USER_LOGIN_FAILED = "User login failed!";
    private static final String USER_LOGIN_FAILED_USER = "User login failed! User : {}";
    private final PasswordOptionRepository passwordOptionRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoginAttemptRepository loginAttemptRepository;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Override
    public final User buildRequestIntoUser(RegisterRequest registerRequest, LoginAttempt loginAttempt) {
        return User
                .builder()
                .name(registerRequest.name())
                .username(registerRequest.username())
                .surname(registerRequest.surname())
                .password(passwordEncoder.encode(registerRequest.password()))
                .loginAttempt(loginAttempt)
                .build();
    }

    @Override
    public final void executeUserAuthenticationProcess(LoginAttempt loginAttempt, LoginRequest loginRequest) {
        try {
            loginAttempt.incrementAttempts();

            Optional<PasswordOption> passwordOption = passwordOptionRepository.findByUserAndPasswordCombination(
                    loginRequest.username(),
                    loginRequest.codedCombination()
            );
            checkIfCombinationHashValid(passwordOption, loginRequest.passwordCombination());

            loginAttempt.resetAttempts();

        } finally {
            loginAttemptRepository.save(loginAttempt);
        }
    }

    private void checkIfCombinationHashValid(Optional<PasswordOption> passwordOption, CharSequence passwordCombination) {
        passwordOption.ifPresentOrElse(
                option -> {
                    if (!passwordEncoder.matches(passwordCombination, option.getCombinationHash())) {
                        log.error(USER_LOGIN_FAILED_USER, option.getUser().getUsername());

                        throw new UsernameNotFoundException(USER_LOGIN_FAILED);
                    }
                },
                () -> {
                    log.error("Combination has not be found!");

                    throw new UsernameNotFoundException(USER_LOGIN_FAILED);
                }
        );
    }

    @Override
    public final User executePasswordResetProcess(ResetPasswordRequest request, User user) {
        JwtToken jwtToken = tokenRepository.findByTokenAndUser(request.resetPasswordToken(), user)
                .orElseThrow(
                        () -> new TokenDoesNotExistException("Token does not exist!")
                );
        log.info("Token to reset passwordCombination : {}", jwtToken);

        String encodedPassword = passwordEncoder.encode(request.newPassword());

        log.info("Setting new passwordCombination : {}", encodedPassword);

        user.setPassword(encodedPassword);

        log.info("Saving new user data and deleting token : {}", jwtToken);

        passwordOptionRepository.deleteAllByUserUsername(user.getUsername());

        tokenRepository.deleteById(jwtToken.getTokenId());

        return userRepository.save(user);
    }

    @Override
    public final void savePasswordResetToken(String passwordRefreshToken, User user) {
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
