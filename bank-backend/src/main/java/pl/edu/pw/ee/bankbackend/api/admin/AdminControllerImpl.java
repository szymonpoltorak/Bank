package pl.edu.pw.ee.bankbackend.api.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.bankbackend.api.admin.interfaces.AdminController;
import pl.edu.pw.ee.bankbackend.api.auth.constants.AuthMappings;
import pl.edu.pw.ee.bankbackend.api.auth.data.RegisterRequest;
import pl.edu.pw.ee.bankbackend.api.auth.interfaces.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/admin")
public class AdminControllerImpl implements AdminController {
    private final AuthService authService;

    @Override
    @PostMapping(value = AuthMappings.REGISTER_MAPPING)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void register(@Valid @RequestBody RegisterRequest registerRequest,
                                 @RequestHeader("User-Agent") String userAgent) {
        authService.register(registerRequest, userAgent);
    }
}
