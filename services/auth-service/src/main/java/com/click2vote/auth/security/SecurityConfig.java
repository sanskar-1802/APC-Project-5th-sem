
// package com.click2vote.auth.security;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// public class SecurityConfig {

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .csrf(csrf -> csrf.disable())
//             .headers(headers -> headers.frameOptions().disable())
//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers("/actuator/**").permitAll()
//                 .requestMatchers("/api/auth/**").permitAll()
//                 .requestMatchers("/h2-console/**").permitAll()
//                 .anyRequest().authenticated()
//             )
//             .sessionManagement(session -> session.sessionCreationPolicy(
//                 org.springframework.security.config.http.SessionCreationPolicy.STATELESS
//             )); // make it stateless for JWT

//         // JWT filter will be added here later
//         return http.build();
//     }
// }

package com.click2vote.auth.security;

import com.click2vote.auth.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final AuthService authService;
    private final String secret = "your-very-strong-secret-key-change-this"; // ⚠️ move to application.yml later

    public SecurityConfig(AuthService authService) {
        this.authService = authService;
    }

    @Bean
  
public PasswordEncoder passwordEncoder() {
    return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
}

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Create filter with secret + service
        JwtAuthFilter jwtFilter = new JwtAuthFilter(secret, authService);

        http
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions().disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()
            )
            // Add our JWT filter before Spring’s default UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}





