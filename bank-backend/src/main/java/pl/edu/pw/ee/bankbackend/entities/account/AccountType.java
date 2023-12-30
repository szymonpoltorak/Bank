package pl.edu.pw.ee.bankbackend.entities.account;

public enum AccountType {
    SAVING, MAIN;

    public String toAccountName() {
        return switch (this) {
            case SAVING -> "Saving Account";

            case MAIN -> "Main Account";
        };
    }
}
