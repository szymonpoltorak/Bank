package pl.edu.pw.ee.bankbackend.api.auth.data;

import lombok.Builder;

@Builder
public record TokenResponse(boolean isAuthTokenValid) {
}
