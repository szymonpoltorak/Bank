package pl.edu.pw.ee.bankbackend.api.admin.interfaces;

import pl.edu.pw.ee.bankbackend.api.auth.data.RegisterRequest;

public interface AdminController {
    void register(RegisterRequest userRequest, String userAgent);
}
