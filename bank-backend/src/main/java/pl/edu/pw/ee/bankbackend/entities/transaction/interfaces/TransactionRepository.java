package pl.edu.pw.ee.bankbackend.entities.transaction.interfaces;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pw.ee.bankbackend.entities.transaction.Transaction;
import pl.edu.pw.ee.bankbackend.entities.user.User;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select t from Transaction t where t.from = ?1 or t.to = ?1 order by t.date desc")
    List<Transaction> findAllByUser(User user);
}
