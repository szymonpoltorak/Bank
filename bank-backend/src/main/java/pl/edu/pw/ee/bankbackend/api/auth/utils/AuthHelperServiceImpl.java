package pl.edu.pw.ee.bankbackend.api.auth.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.bankbackend.api.auth.data.LoginRequest;
import pl.edu.pw.ee.bankbackend.api.auth.data.RegisterRequest;
import pl.edu.pw.ee.bankbackend.api.auth.data.ResetPasswordRequest;
import pl.edu.pw.ee.bankbackend.api.auth.utils.interfaces.AuthHelperService;
import pl.edu.pw.ee.bankbackend.entities.attempts.LoginAttempt;
import pl.edu.pw.ee.bankbackend.entities.attempts.interfaces.LoginAttemptRepository;
import pl.edu.pw.ee.bankbackend.entities.token.JwtToken;
import pl.edu.pw.ee.bankbackend.entities.token.TokenType;
import pl.edu.pw.ee.bankbackend.entities.token.interfaces.TokenRepository;
import pl.edu.pw.ee.bankbackend.entities.user.User;
import pl.edu.pw.ee.bankbackend.entities.user.interfaces.UserRepository;
import pl.edu.pw.ee.bankbackend.exceptions.auth.throwable.TokenDoesNotExistException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthHelperServiceImpl implements AuthHelperService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
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

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.username(), loginRequest.password())
            );
            loginAttempt.resetAttempts();

        } catch (AuthenticationException e) {
            log.error("User login failed! User : {}", loginRequest.username());

            throw new UsernameNotFoundException("User login failed!", e);
        } finally {
            loginAttemptRepository.save(loginAttempt);
        }
    }

    @Override
    public final void executePasswordResetProcess(ResetPasswordRequest request, User user) {
        JwtToken jwtToken = tokenRepository.findByTokenAndUser(request.resetPasswordToken(), user)
                .orElseThrow(
                        () -> new TokenDoesNotExistException("Token does not exist!")
                );
        log.info("Token to reset password : {}", jwtToken);

        String encodedPassword = passwordEncoder.encode(request.newPassword());

        log.info("Setting new password : {}", encodedPassword);

        user.setPassword(encodedPassword);

        log.info("Saving new user data and deleting token : {}", jwtToken);

        userRepository.save(user);

        tokenRepository.deleteById(jwtToken.getTokenId());
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
