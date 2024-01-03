package pl.edu.pw.ee.bankbackend.entities.account.interfaces;

public interface Account {
    void addAccountBalance(float balance);

    void subtractAccountBalance(float amount);
}
