package pl.edu.pw.ee.bankbackend.api.auth.devices.interfaces;


import pl.edu.pw.ee.bankbackend.api.auth.devices.data.LoggedDeviceResponse;
import pl.edu.pw.ee.bankbackend.entities.user.User;

import java.util.List;

@FunctionalInterface
public interface DeviceController {
    List<LoggedDeviceResponse> getLoggedDevicesOnPage(int page, User user);
}
