package pl.edu.pw.ee.bankbackend.entities.devices;

public enum DeviceType {
    DESKTOP,
    MOBILE;

    public static DeviceType getDeviceType(String userAgent) {
        if (userAgent.contains("Android") || userAgent.contains("iPhone")) {
            return MOBILE;
        }
        return DESKTOP;
    }
}
