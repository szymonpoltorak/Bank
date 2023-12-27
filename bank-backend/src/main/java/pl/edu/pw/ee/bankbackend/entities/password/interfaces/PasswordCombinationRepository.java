package pl.edu.pw.ee.bankbackend.entities.password.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pw.ee.bankbackend.entities.password.PasswordCombination;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordCombinationRepository extends JpaRepository<PasswordCombination, Long> {
    @Query("SELECT pc FROM PasswordCombination pc WHERE pc.user.username = ?1 AND pc.codedCombination = ?2")
    Optional<PasswordCombination> findByUserAndPasswordCombination(String username, String passwordCombination);

    List<PasswordCombination> findAllByUserUsername(String username);

    void deleteAllByUserUsername(String username);
}
