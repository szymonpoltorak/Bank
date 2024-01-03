package pl.edu.pw.ee.bankbackend.entities.account.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pw.ee.bankbackend.entities.account.BankAccount;
import pl.edu.pw.ee.bankbackend.entities.user.User;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByUser(User user);

    boolean existsByBillNumber(String billNumber);

    Optional<BankAccount> findByBillNumber(String billNumber);
}
