package pl.edu.pw.ee.bankbackend.api.auth.devices;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.bankbackend.api.auth.devices.data.LoggedDeviceResponse;
import pl.edu.pw.ee.bankbackend.api.auth.devices.interfaces.DeviceController;
import pl.edu.pw.ee.bankbackend.api.auth.devices.interfaces.DeviceService;
import pl.edu.pw.ee.bankbackend.entities.user.User;

import java.util.List;

import static pl.edu.pw.ee.bankbackend.api.auth.devices.constants.DeviceMappings.DEVICE_MAPPING;
import static pl.edu.pw.ee.bankbackend.api.auth.devices.constants.DeviceMappings.GET_LOGGED_DEVICES;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = DEVICE_MAPPING)
public class DeviceControllerImpl implements DeviceController {
    private final DeviceService deviceService;

    @Override
    @GetMapping(value = GET_LOGGED_DEVICES)
    public final List<LoggedDeviceResponse> getLoggedDevicesOnPage(@RequestParam("page") int page,
                                                                   @AuthenticationPrincipal User user) {
        return deviceService.getLoggedDevicesOnPage(page, user);
    }
}
