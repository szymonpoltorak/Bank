package pl.edu.pw.ee.bankbackend.entities.password.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pw.ee.bankbackend.entities.password.PasswordOption;

@Repository
public interface PasswordOptionRepository extends JpaRepository<PasswordOption, Long> {
}
