package pl.edu.pw.ee.bankbackend.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.bankbackend.api.user.data.UserResponse;
import pl.edu.pw.ee.bankbackend.api.user.interfaces.UserController;
import pl.edu.pw.ee.bankbackend.api.user.interfaces.UserService;
import pl.edu.pw.ee.bankbackend.entities.user.User;

import static pl.edu.pw.ee.bankbackend.api.user.constants.UserMappings.GET_USER_INFO;
import static pl.edu.pw.ee.bankbackend.api.user.constants.UserMappings.USER_API_ENDPOINTS;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = USER_API_ENDPOINTS)
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    @GetMapping(value = GET_USER_INFO)
    public final UserResponse getUserAccountInfo(@AuthenticationPrincipal User user) {
        return userService.getUserAccountInfo(user);
    }
}
