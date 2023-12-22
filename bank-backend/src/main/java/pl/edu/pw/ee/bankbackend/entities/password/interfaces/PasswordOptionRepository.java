package pl.edu.pw.ee.bankbackend.entities.password.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pw.ee.bankbackend.entities.password.PasswordOption;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordOptionRepository extends JpaRepository<PasswordOption, Long> {
    @Query("SELECT po FROM PasswordOption po WHERE po.user.username = ?1 AND po.codedCombination = ?2")
    Optional<PasswordOption> findByUserAndPasswordCombination(String username, String passwordCombination);
}
