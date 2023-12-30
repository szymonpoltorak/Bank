package pl.edu.pw.ee.bankbackend.api.user.data;

import lombok.Builder;

@Builder
public record UserResponse(String username, String name, String surname,
                           String idCardNumber, String billNumber) {
}
