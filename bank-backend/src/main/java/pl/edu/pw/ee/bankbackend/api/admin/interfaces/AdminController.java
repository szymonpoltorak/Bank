package pl.edu.pw.ee.bankbackend.api.admin.interfaces;

import pl.edu.pw.ee.bankbackend.api.auth.data.RegisterRequest;

@FunctionalInterface
public interface AdminController {
    void register(RegisterRequest userRequest, String userAgent);
}
