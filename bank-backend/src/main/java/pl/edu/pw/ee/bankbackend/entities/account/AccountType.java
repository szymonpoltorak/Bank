package pl.edu.pw.ee.bankbackend.entities.account;

import pl.edu.pw.ee.bankbackend.entities.account.interfaces.AccountNameMapper;

public enum AccountType implements AccountNameMapper {
    SAVING, MAIN;

    @Override
    public final String toAccountName() {
        return switch (this) {
            case SAVING -> "Saving Account";

            case MAIN -> "Main Account";
        };
    }
}
