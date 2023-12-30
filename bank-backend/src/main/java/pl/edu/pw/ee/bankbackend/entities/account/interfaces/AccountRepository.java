package pl.edu.pw.ee.bankbackend.entities.account.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pw.ee.bankbackend.entities.account.Account;
import pl.edu.pw.ee.bankbackend.entities.user.User;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUser(User user);

    boolean existsByBillNumber(String billNumber);

    Optional<Account> findByBillNumber(String billNumber);
}
