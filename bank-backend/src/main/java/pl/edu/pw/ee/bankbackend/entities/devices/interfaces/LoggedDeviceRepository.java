package pl.edu.pw.ee.bankbackend.entities.devices.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pw.ee.bankbackend.entities.devices.LoggedDevice;
import pl.edu.pw.ee.bankbackend.entities.user.User;

@Repository
public interface LoggedDeviceRepository extends JpaRepository<LoggedDevice, Long> {
    Page<LoggedDevice> findAllByUser(User user, Pageable pageable);
}
