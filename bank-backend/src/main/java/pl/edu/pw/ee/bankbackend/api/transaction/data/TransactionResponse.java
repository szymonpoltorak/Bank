package pl.edu.pw.ee.bankbackend.api.transaction.data;

import lombok.Builder;

@Builder
public record TransactionResponse(String from, String to, float amount, String title, String date) {
}
