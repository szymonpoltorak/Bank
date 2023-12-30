package pl.edu.pw.ee.bankbackend.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.edu.pw.ee.bankbackend.api.auth.data.RegisterRequest;
import pl.edu.pw.ee.bankbackend.api.auth.interfaces.AuthService;
import pl.edu.pw.ee.bankbackend.config.interfaces.AppConfiguration;
import pl.edu.pw.ee.bankbackend.entities.account.AccountType;
import pl.edu.pw.ee.bankbackend.entities.attempts.LoginAttempt;
import pl.edu.pw.ee.bankbackend.entities.attempts.interfaces.LoginAttemptRepository;
import pl.edu.pw.ee.bankbackend.entities.user.User;
import pl.edu.pw.ee.bankbackend.entities.user.UserRole;
import pl.edu.pw.ee.bankbackend.entities.user.interfaces.UserRepository;

import java.security.SecureRandom;
import java.util.List;

import static pl.edu.pw.ee.bankbackend.config.constants.CorsConfig.ALLOWED_REQUESTS;
import static pl.edu.pw.ee.bankbackend.config.constants.CorsConfig.API_PATTERN;
import static pl.edu.pw.ee.bankbackend.config.constants.CorsConfig.CONTENT_TYPE_HEADER;
import static pl.edu.pw.ee.bankbackend.config.constants.CorsConfig.CORS_ADDRESSES;
import static pl.edu.pw.ee.bankbackend.config.constants.Headers.AUTH_HEADER;


@RequiredArgsConstructor
@Configuration
public class AppConfigurationImpl implements AppConfiguration {
    private static final int BCRYPT_STRENGTH = 15;
    private final UserRepository userRepository;

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    @Bean
    @Override
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        configuration.setAllowedOrigins(CORS_ADDRESSES);
        configuration.setAllowedMethods(ALLOWED_REQUESTS);
        configuration.setAllowedHeaders(List.of(AUTH_HEADER, CONTENT_TYPE_HEADER));

        source.registerCorsConfiguration(API_PATTERN, configuration);

        return source;
    }

    @Bean
    @Override
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    @Override
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y,
                BCRYPT_STRENGTH, new SecureRandom(SecureRandom.getSeed(BCRYPT_STRENGTH)));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public CommandLineRunner commandLineRunner(AuthService authService) {
        return args -> {
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537";

            RegisterRequest registerRequest = RegisterRequest
                    .builder()
                    .name("admin")
                    .surname("admin")
                    .username("admin@gmail.com")
                    .password("Admin123!?")
                    .idCardNumber("ABC123456")
                    .accountType(AccountType.MAIN)
                    .userRole(UserRole.ADMIN)
                    .build();
            authService.register(registerRequest, userAgent);
        };
    }
}
