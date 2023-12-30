package pl.edu.pw.ee.bankbackend.api.user.interfaces;

import pl.edu.pw.ee.bankbackend.api.user.data.UserResponse;
import pl.edu.pw.ee.bankbackend.entities.user.User;

@FunctionalInterface
public interface UserController {
    UserResponse getUserAccountInfo(User user);
}
