package pl.edu.pw.ee.bankbackend.api.auth.data;

import lombok.Builder;

@Builder
public record LoginRequest(String username, String passwordCombination, String codedCombination) {
}
