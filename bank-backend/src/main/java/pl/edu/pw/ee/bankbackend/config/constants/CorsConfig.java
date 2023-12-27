package pl.edu.pw.ee.bankbackend.config.constants;

import java.util.List;

public final class CorsConfig {
    public static final List<String> ALLOWED_REQUESTS = List.of("GET", "POST", "DELETE", "PATCH", "OPTIONS");

    public static final List<String> CORS_ADDRESSES = List.of(
            "https://localhost", "https://localhost/", "http://localhost:4200"
    );

    public static final String CONTENT_TYPE_HEADER = "Content-Type";

    public static final String API_PATTERN = "/api/v1/**";

    private CorsConfig() {
    }

    public static String getApiPattern(String requestMapping) {
        return String.format("%s%s", API_PATTERN, requestMapping);
    }
}
