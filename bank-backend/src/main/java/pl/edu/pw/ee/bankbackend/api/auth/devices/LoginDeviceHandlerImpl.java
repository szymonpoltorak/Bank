package pl.edu.pw.ee.bankbackend.api.auth.devices;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.edu.pw.ee.bankbackend.api.auth.interfaces.LoginDeviceHandler;
import pl.edu.pw.ee.bankbackend.entities.devices.DeviceType;
import pl.edu.pw.ee.bankbackend.entities.devices.LoggedDevice;
import pl.edu.pw.ee.bankbackend.entities.devices.interfaces.LoggedDeviceRepository;
import pl.edu.pw.ee.bankbackend.entities.user.User;

import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginDeviceHandlerImpl implements LoginDeviceHandler {
    private final LoggedDeviceRepository loggedDeviceRepository;

    @Override
    public final void addNewDeviceToUserLoggedInDevices(User user, HttpServletRequest request) {
        String remoteAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        DeviceType deviceType = DeviceType.getDeviceType(userAgent);

        log.info("Adding new device to user: {} with ip: {}", user, remoteAddress);
        log.info("Login device type: {}", deviceType);

        LoggedDevice loggedDevice = LoggedDevice
                .builder()
                .ipAddress(remoteAddress)
                .deviceType(deviceType)
                .dateOfLogin(LocalDate.now())
                .timeOfLogin(LocalTime.now())
                .user(user)
                .build();
        log.info("Logged device: {}", loggedDevice);

        loggedDeviceRepository.save(loggedDevice);
    }
}
