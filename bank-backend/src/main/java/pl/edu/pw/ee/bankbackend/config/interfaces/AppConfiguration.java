package pl.edu.pw.ee.bankbackend.config.interfaces;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfigurationSource;
import pl.edu.pw.ee.bankbackend.api.auth.interfaces.AuthService;

public interface AppConfiguration {
    UserDetailsService userDetailsService();

    AuthenticationProvider authenticationProvider();

    PasswordEncoder passwordEncoder();

    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception;

    CorsConfigurationSource corsConfigurationSource();

    CommandLineRunner commandLineRunner(AuthService authService);
}
