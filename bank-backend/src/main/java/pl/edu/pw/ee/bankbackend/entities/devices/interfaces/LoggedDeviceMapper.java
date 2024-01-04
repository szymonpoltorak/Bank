package pl.edu.pw.ee.bankbackend.entities.devices.interfaces;

import org.mapstruct.Mapper;
import pl.edu.pw.ee.bankbackend.api.auth.devices.data.LoggedDeviceResponse;
import pl.edu.pw.ee.bankbackend.entities.devices.LoggedDevice;

@FunctionalInterface
@Mapper(componentModel = "spring")
public interface LoggedDeviceMapper {
    LoggedDeviceResponse toLoggedDeviceResponse(LoggedDevice loggedDevice);
}
