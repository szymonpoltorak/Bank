package pl.edu.pw.ee.bankbackend.api.auth.data;

import lombok.Builder;

@Builder
public record AuthResponse(String authToken, String refreshToken) {
}
