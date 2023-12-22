package pl.edu.pw.ee.bankbackend.api.auth.utils.interfaces;

import pl.edu.pw.ee.bankbackend.entities.user.User;

import java.util.List;

@FunctionalInterface
public interface PasswordCombinationService {
    void generateCombinationsForPassword(String password, User user);
}
