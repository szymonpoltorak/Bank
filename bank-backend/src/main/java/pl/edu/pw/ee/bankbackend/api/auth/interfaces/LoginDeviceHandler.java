package pl.edu.pw.ee.bankbackend.api.auth.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import pl.edu.pw.ee.bankbackend.entities.user.User;

@FunctionalInterface
public interface LoginDeviceHandler {
    void addNewDeviceToUserLoggedInDevices(User user, HttpServletRequest request);
}
