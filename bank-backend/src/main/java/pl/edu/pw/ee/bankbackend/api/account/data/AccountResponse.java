package pl.edu.pw.ee.bankbackend.api.account.data;

import lombok.Builder;
import pl.edu.pw.ee.bankbackend.entities.account.AccountType;

@Builder
public record AccountResponse(String billNumber, float balance, String accountName, AccountType accountType) {
}
