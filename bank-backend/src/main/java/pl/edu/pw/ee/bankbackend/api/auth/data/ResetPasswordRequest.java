package pl.edu.pw.ee.bankbackend.api.auth.data;


import jakarta.validation.constraints.NotNull;
import pl.edu.pw.ee.bankbackend.entities.user.interfaces.Password;

public record ResetPasswordRequest(@Password String newPassword, @NotNull String resetPasswordToken) {
}
