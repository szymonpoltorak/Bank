package pl.edu.pw.ee.bankbackend.api.transaction.data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TransactionRequest(@NotNull @Size(min = 26, max = 26)
                                 @Pattern(regexp = "\\d+$")String to,
                                 @NotNull float amount,
                                 @NotNull String title) {
}
