package pl.edu.pw.ee.bankbackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import pl.edu.pw.ee.bankbackend.config.constants.Properties;
import pl.edu.pw.ee.bankbackend.config.interfaces.SecurityConfiguration;
import pl.edu.pw.ee.bankbackend.config.jwt.interfaces.JwtAuthenticationFilter;
import pl.edu.pw.ee.bankbackend.entities.user.UserRole;

import static pl.edu.pw.ee.bankbackend.config.constants.Matchers.AUTH_MATCHERS;
import static pl.edu.pw.ee.bankbackend.config.constants.Matchers.LOGOUT_URL;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        jsr250Enabled = true,
        securedEnabled = true
)
@RequiredArgsConstructor
public class SecurityConfigurationImpl implements SecurityConfiguration {
    @Value(Properties.FRONTEND_URL)
    private String frontendUrl;
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final LogoutHandler logoutHandler;

    @Bean
    @Override
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(LOGOUT_URL)
                        .permitAll()

                        .requestMatchers(AUTH_MATCHERS)
                        .permitAll()

                        .requestMatchers("/api/v1/admin")
                        .hasAuthority(UserRole.ADMIN.name())

                        .anyRequest()
                        .authenticated()
                )
                .cors(Customizer.withDefaults())
                .headers(headers -> headers
                        .xssProtection(Customizer.withDefaults())
                        .contentSecurityPolicy(policy -> policy
                                .policyDirectives(buildContentPolicyDirective())
                        )
                )
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl(LOGOUT_URL)
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(
                                (request, response, authentication) -> SecurityContextHolder.clearContext()
                        )
                );
        return httpSecurity.build();
    }

    private String buildContentPolicyDirective() {
        return String.format("form-action 'self' %s; img-src 'self'; child-src 'none'; script-src 'self'", frontendUrl);
    }
}
