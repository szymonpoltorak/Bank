package pl.edu.pw.ee.bankbackend.api.auth.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.bankbackend.api.auth.data.LoginRequest;
import pl.edu.pw.ee.bankbackend.api.auth.data.RegisterRequest;
import pl.edu.pw.ee.bankbackend.api.auth.utils.interfaces.AuthHelperService;
import pl.edu.pw.ee.bankbackend.entities.attempts.LoginAttempt;
import pl.edu.pw.ee.bankbackend.entities.attempts.interfaces.LoginAttemptRepository;
import pl.edu.pw.ee.bankbackend.entities.password.PasswordCombination;
import pl.edu.pw.ee.bankbackend.entities.password.interfaces.PasswordCombinationRepository;
import pl.edu.pw.ee.bankbackend.entities.user.User;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthHelperServiceImpl implements AuthHelperService {
    private static final String USER_LOGIN_FAILED = "User login failed!";
    private static final String USER_LOGIN_FAILED_USER = "User login failed! User : {}";
    private final PasswordCombinationRepository passwordCombinationRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoginAttemptRepository loginAttemptRepository;

    @Override
    public final User buildRequestIntoUser(RegisterRequest registerRequest, LoginAttempt loginAttempt) {
        return User
                .builder()
                .name(registerRequest.name())
                .username(registerRequest.username())
                .surname(registerRequest.surname())
                .password(passwordEncoder.encode(registerRequest.password()))
                .idCardNumber(registerRequest.idCardNumber())
                .role(registerRequest.userRole())
                .loginAttempt(loginAttempt)
                .build();
    }

    @Override
    public final void executeUserAuthenticationProcess(LoginAttempt loginAttempt, LoginRequest loginRequest) {
        try {
            loginAttempt.incrementAttempts();

            Optional<PasswordCombination> passwordOption = passwordCombinationRepository.findByUserAndPasswordCombination(
                    loginRequest.username(),
                    loginRequest.codedCombination()
            );
            checkIfCombinationHashValid(passwordOption, loginRequest.passwordCombination());

            loginAttempt.resetAttempts();

        } finally {
            loginAttemptRepository.save(loginAttempt);
        }
    }

    private void checkIfCombinationHashValid(Optional<PasswordCombination> passwordOption, CharSequence passwordCombination) {
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
}
