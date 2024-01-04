package pl.edu.pw.ee.bankbackend.api.auth.interfaces;

import pl.edu.pw.ee.bankbackend.entities.user.User;

@FunctionalInterface
public interface LoginDeviceHandler {
    void addNewDeviceToUserLoggedInDevices(User user, String userAgent);
}
