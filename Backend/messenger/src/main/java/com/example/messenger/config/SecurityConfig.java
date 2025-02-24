
package com.example.messenger.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/user", "/auth/user/db").authenticated() // Only authenticated users
                        .anyRequest().permitAll() // Other routes are public
                )

                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/auth/user", true) // Redirect after successful login
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout") // Logout URL
                        .logoutSuccessUrl("/oauth2/authorization/google").permitAll() // Redirect to login page after logout
                        .invalidateHttpSession(true) // Invalidate the session
                        .clearAuthentication(true) // Clear the authentication
                        .deleteCookies("JSESSIONID") // Delete cookies if any
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/auth/logout") // Ignore CSRF for logout
                );
        return http.build();
    }
}