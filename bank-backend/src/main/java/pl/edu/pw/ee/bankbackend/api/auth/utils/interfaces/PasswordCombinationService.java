package pl.edu.pw.ee.bankbackend.api.auth.utils.interfaces;

import pl.edu.pw.ee.bankbackend.entities.user.User;

public interface PasswordCombinationService {
    void generateCombinationsForPassword(String password, User user);

    String getPasswordCombinationForUser(String username);
}
