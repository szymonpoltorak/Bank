package pl.edu.pw.ee.bankbackend.api.auth;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.bankbackend.api.auth.data.AuthResponse;
import pl.edu.pw.ee.bankbackend.api.auth.data.LoginRequest;
import pl.edu.pw.ee.bankbackend.api.auth.data.RegisterRequest;
import pl.edu.pw.ee.bankbackend.api.auth.data.ResetPasswordRequest;
import pl.edu.pw.ee.bankbackend.api.auth.data.SimpleStringResponse;
import pl.edu.pw.ee.bankbackend.api.auth.interfaces.AuthService;
import pl.edu.pw.ee.bankbackend.api.auth.interfaces.LoginDeviceHandler;
import pl.edu.pw.ee.bankbackend.api.auth.utils.interfaces.AuthHelperService;
import pl.edu.pw.ee.bankbackend.api.auth.utils.interfaces.PasswordCombinationService;
import pl.edu.pw.ee.bankbackend.config.constants.Properties;
import pl.edu.pw.ee.bankbackend.config.constants.TokenRevokeStatus;
import pl.edu.pw.ee.bankbackend.config.jwt.interfaces.JwtService;
import pl.edu.pw.ee.bankbackend.config.jwt.interfaces.TokenManagerService;
import pl.edu.pw.ee.bankbackend.entities.attempts.LoginAttempt;
import pl.edu.pw.ee.bankbackend.entities.attempts.interfaces.LoginAttemptRepository;
import pl.edu.pw.ee.bankbackend.entities.user.User;
import pl.edu.pw.ee.bankbackend.entities.user.interfaces.UserRepository;
import pl.edu.pw.ee.bankbackend.exceptions.auth.throwable.InvalidTokenException;
import pl.edu.pw.ee.bankbackend.exceptions.auth.throwable.TokenDoesNotExistException;
import pl.edu.pw.ee.bankbackend.exceptions.auth.throwable.UserAlreadyExistsException;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final String USER_NOT_EXIST_MESSAGE = "Such user does not exist!";
    private static final String BUILDING_TOKEN_RESPONSE_MESSAGE = "Building token response for user : {}";
    private static final long PASSWORD_REFRESH_TOKEN_TIME = 600_000L;

    @Value(Properties.FRONTEND_URL)
    private String frontendUrl;
    
    private final UserRepository userRepository;
    private final TokenManagerService tokenManager;
    private final LoginAttemptRepository loginAttemptRepository;
    private final LoginDeviceHandler loginDeviceFilter;
    private final JwtService jwtService;
    private final AuthHelperService authHelperService;
    private final PasswordCombinationService passwordCombinationService;

    @Override
    public final AuthResponse register(RegisterRequest registerRequest, HttpServletRequest request) {
        log.info("Registering user with data: \n{}", registerRequest);

        validateUserRegisterData(registerRequest);

        LoginAttempt loginAttempt = LoginAttempt.newInstance();

        LoginAttempt newLoginAttempt = loginAttemptRepository.save(loginAttempt);

        User user = authHelperService.buildRequestIntoUser(registerRequest, newLoginAttempt);

        User newUser = userRepository.save(user);

        loginDeviceFilter.addNewDeviceToUserLoggedInDevices(newUser, request);

        passwordCombinationService.generateCombinationsForPassword(registerRequest.password(), newUser);

        log.info(BUILDING_TOKEN_RESPONSE_MESSAGE, newUser);

        return tokenManager.buildTokensIntoResponse(newUser, TokenRevokeStatus.NOT_TO_REVOKE);
    }

    @Override
    public final AuthResponse login(LoginRequest loginRequest, HttpServletRequest request) {
        log.info("Logging user with data: \n{}", loginRequest);

        String username = loginRequest.username();

        User user = validateUserLoginAccount(username);

        LoginAttempt loginAttempt = user.getLoginAttempt();

        authHelperService.executeUserAuthenticationProcess(loginAttempt, loginRequest);

        loginDeviceFilter.addNewDeviceToUserLoggedInDevices(user, request);

        log.info(BUILDING_TOKEN_RESPONSE_MESSAGE, user);

        return tokenManager.buildTokensIntoResponse(user, TokenRevokeStatus.TO_REVOKE);
    }

    @Override
    public final AuthResponse refreshToken(String refreshToken) {
        log.info("Refresh token : {}", refreshToken);

        User user = validateRefreshTokenData(refreshToken);

        String authToken = jwtService.generateToken(user);

        log.info("New auth token : {}\nFor user : {}", authToken, user);

        tokenManager.revokeUserTokens(user);

        tokenManager.saveUsersToken(authToken, user);

        return tokenManager.buildTokensIntoResponse(authToken, refreshToken);
    }

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

        authHelperService.savePasswordResetToken(passwordRefreshToken, user);

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

        User newUser = authHelperService.executePasswordResetProcess(request, user);

        passwordCombinationService.generateCombinationsForPassword(request.newPassword(), newUser);

        return new SimpleStringResponse(username);
    }

    @Override
    public final SimpleStringResponse getPasswordCombinationForUser(String username) {
        log.info("Getting passwordCombination for user : {}", username);

        String passwordCombination = passwordCombinationService.getPasswordCombinationForUser(username);

        log.info("Password combination : {}", passwordCombination);

        return new SimpleStringResponse(passwordCombination);
    }

    private User validateUserLoginAccount(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(USER_NOT_EXIST_MESSAGE)
        );
        if (!user.isAccountNonLocked()) {
            log.error("User is locked! User : {}", user);

            throw new UsernameNotFoundException("User is locked!");
        }
        return user;
    }

    private void validateUserRegisterData(RegisterRequest registerRequest) {
        Optional<User> existingUser = userRepository.findByUsername(registerRequest.username());

        existingUser.ifPresent(user -> {
            log.error("User already exists! Found user: {}", user);

            throw new UserAlreadyExistsException("User already exists!");
        });
    }

    private User validateRefreshTokenData(String refreshToken) {
        if (refreshToken == null) {
            throw new TokenDoesNotExistException("Token does not exist!");
        }
        Optional<String> usernameOptional = jwtService.getClaimFromToken(refreshToken, Claims::getSubject);

        if (usernameOptional.isEmpty()) {
            throw new UsernameNotFoundException(USER_NOT_EXIST_MESSAGE);
        }
        String username = usernameOptional.get();

        log.info("User of stringResponse : {}", username);

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(USER_NOT_EXIST_MESSAGE)
        );

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new InvalidTokenException("Token is not valid!");
        }
        return user;
    }
}
