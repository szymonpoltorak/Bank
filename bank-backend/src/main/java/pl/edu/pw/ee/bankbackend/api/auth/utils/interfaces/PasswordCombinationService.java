package pl.edu.pw.ee.bankbackend.api.auth.utils.interfaces;

import pl.edu.pw.ee.bankbackend.entities.user.User;

@FunctionalInterface
public interface PasswordCombinationService {
    void generateCimbinationsForPassword(String password, User user);
}
