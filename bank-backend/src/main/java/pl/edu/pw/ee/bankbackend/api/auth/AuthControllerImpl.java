package pl.edu.pw.ee.bankbackend.api.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.bankbackend.api.auth.constants.AuthMappings;
import pl.edu.pw.ee.bankbackend.api.auth.data.AuthResponse;
import pl.edu.pw.ee.bankbackend.api.auth.data.LoginRequest;
import pl.edu.pw.ee.bankbackend.api.auth.data.RegisterRequest;
import pl.edu.pw.ee.bankbackend.api.auth.data.ResetPasswordRequest;
import pl.edu.pw.ee.bankbackend.api.auth.data.SimpleStringResponse;
import pl.edu.pw.ee.bankbackend.api.auth.interfaces.AuthController;
import pl.edu.pw.ee.bankbackend.api.auth.interfaces.AuthService;

import static pl.edu.pw.ee.bankbackend.config.constants.Matchers.AUTH_MAPPING;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = AUTH_MAPPING)
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    @Override
    @PostMapping(value = AuthMappings.REGISTER_MAPPING)
    @ResponseStatus(value = HttpStatus.CREATED)
    public final AuthResponse registerUser(@Valid @RequestBody RegisterRequest registerRequest,
                                           HttpServletRequest request) {
        return authService.register(registerRequest, request);
    }

    @Override
    @PostMapping(value = AuthMappings.LOGIN_MAPPING)
    public final AuthResponse loginUser(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        return authService.login(loginRequest, request);
    }

    @Override
    @PostMapping(value = AuthMappings.REFRESH_MAPPING)
    public final AuthResponse refreshUserToken(@RequestParam("refreshToken") String refreshToken) {
        return authService.refreshToken(refreshToken);
    }

    @Override
    @PostMapping(value = AuthMappings.REQUEST_RESET_PASSWORD_MAPPING)
    public final SimpleStringResponse requestResetUsersPassword(@RequestParam("username") String username) {
        return authService.requestResetUsersPassword(username);
    }

    @Override
    @PostMapping(value = AuthMappings.RESET_PASSWORD_MAPPING)
    public final SimpleStringResponse resetUsersPassword(@Valid @RequestBody ResetPasswordRequest request) {
        return authService.resetUsersPassword(request);
    }

    @Override
    @GetMapping(value = AuthMappings.GET_COMBINATION_MAPPING)
    public SimpleStringResponse getPasswordCombinationForUser(@RequestParam("username") String username) {
        return authService.getPasswordCombinationForUser(username);
    }
}
