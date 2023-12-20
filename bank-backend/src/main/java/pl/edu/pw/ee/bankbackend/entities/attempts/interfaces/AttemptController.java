package pl.edu.pw.ee.bankbackend.entities.attempts.interfaces;

public interface AttemptController {
    void incrementAttempts();

    void resetAttempts();

    boolean isAccountNonLocked();
}
