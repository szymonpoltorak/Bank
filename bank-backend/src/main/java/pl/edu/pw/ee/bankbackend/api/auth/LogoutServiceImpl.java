package pl.edu.pw.ee.bankbackend.api.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.bankbackend.config.jwt.interfaces.TokenManagerService;
import pl.edu.pw.ee.bankbackend.entities.token.JwtToken;
import pl.edu.pw.ee.bankbackend.entities.token.interfaces.TokenRepository;
import pl.edu.pw.ee.bankbackend.exceptions.auth.throwable.TokenDoesNotExistException;

import static pl.edu.pw.ee.bankbackend.config.constants.Headers.AUTH_HEADER;
import static pl.edu.pw.ee.bankbackend.config.constants.Headers.TOKEN_HEADER;
import static pl.edu.pw.ee.bankbackend.config.constants.Headers.TOKEN_START_INDEX;


@Slf4j
@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutHandler {
    private final TokenRepository tokenRepository;
    private final TokenManagerService tokenManager;

    @Override
    public final void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader = request.getHeader(AUTH_HEADER);

        if (authHeader == null || !authHeader.startsWith(TOKEN_HEADER)) {
            log.warn("Auth header is null or it does not contain Bearer token");

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

            return;
        }
        String jwt = authHeader.substring(TOKEN_START_INDEX);

        JwtToken token = tokenRepository.findByToken(jwt).orElseThrow(
                () -> new TokenDoesNotExistException("Jwt in header: {}\nToken is null")
        );
        log.info("Jwt in header : {}\nToken is not null", jwt);

        tokenManager.revokeUserTokens(token.getUser());

        SecurityContextHolder.clearContext();
    }
}
