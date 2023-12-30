package pl.edu.pw.ee.bankbackend.api.auth.data;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import pl.edu.pw.ee.bankbackend.entities.account.AccountType;
import pl.edu.pw.ee.bankbackend.entities.user.interfaces.Password;

@Builder
public record RegisterRequest(@NotNull String name, @NotNull String surname, @NotNull String username,
                              @Password String password, @NotNull String idCardNumber,
                              @NotNull AccountType accountType, @NotNull float initialBalance) {
}
