package pl.edu.pw.ee.bankbackend.config.jwt.interfaces;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import pl.edu.pw.ee.bankbackend.entities.user.interfaces.ServiceUser;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface JwtService {
    <T> Optional<T> getClaimFromToken(String jwtToken, Function<Claims, T> claimsHandler);

    String generateToken(ServiceUser userDetails);

    String generateToken(Map<String, Object> additionalClaims, ServiceUser userDetails, long expiration);

    boolean isTokenValid(String jwtToken, UserDetails userDetails);

    Optional<String> getJwtTokenFromRequest(HttpServletRequest request);

    String generateRefreshToken(ServiceUser userDetails);
}
