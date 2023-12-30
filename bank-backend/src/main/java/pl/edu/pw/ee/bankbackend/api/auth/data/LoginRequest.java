package pl.edu.pw.ee.bankbackend.api.auth.data;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LoginRequest(@NotNull String username, @NotNull String passwordCombination,
                           @NotNull String codedCombination) {
}
