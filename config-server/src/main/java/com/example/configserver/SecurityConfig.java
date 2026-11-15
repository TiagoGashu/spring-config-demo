package com.example.configserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())              // webhooks / actuator POSTs have no CSRF token
            .authorizeHttpRequests(auth -> auth
                // Git webhooks (GitHub/GitLab/Bitbucket) cannot send basic-auth easily.
                // Restrict this path at the network level (IP allow-list / API gateway).
                .requestMatchers("/monitor/**").permitAll()
                .requestMatchers("/actuator/health/**").permitAll()
                .anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
