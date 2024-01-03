package pl.edu.pw.ee.bankbackend.entities.password.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pw.ee.bankbackend.entities.password.PasswordCombination;
import pl.edu.pw.ee.bankbackend.entities.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordCombinationRepository extends JpaRepository<PasswordCombination, Long> {
    @Query("SELECT pc FROM PasswordCombination pc WHERE pc.user.username = ?1 AND pc.codedCombination = ?2")
    Optional<PasswordCombination> findByUserAndPasswordCombination(String username, String passwordCombination);

    List<PasswordCombination> findAllByUserUsername(String username);

    @Query("SELECT pc.passwordCombinationId FROM PasswordCombination pc WHERE pc.user = ?1")
    List<Long> findAllByUser(User user);
}
