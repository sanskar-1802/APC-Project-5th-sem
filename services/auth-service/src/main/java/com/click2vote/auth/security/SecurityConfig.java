// package com.click2vote.auth.security;

// import com.click2vote.auth.service.AuthService;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// @Configuration
// public class SecurityConfig {

//     private final AuthService authService;
//     private final String secret = "your-very-strong-secret-key-change-this";

//     public SecurityConfig(AuthService authService) {
//         this.authService = authService;
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
//     }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         JwtAuthFilter jwtFilter = new JwtAuthFilter(secret, authService);

//         http
//             .csrf(csrf -> csrf.disable())
//             .headers(headers -> headers.frameOptions().disable())
//             .authorizeHttpRequests(auth -> auth
//                 // allow unauthenticated access to auth endpoints
//                 .requestMatchers("/api/auth/**").permitAll()
//                 .requestMatchers("/h2-console/**").permitAll()
//                 .requestMatchers("/actuator/**").permitAll()
//                 .anyRequest().authenticated()
//             )
//             .formLogin(form -> form.disable())
//             .httpBasic(basic -> basic.disable());

//         // Only protect requests AFTER authentication
//         http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    private final AuthService authService;
    private final String secret = "your-very-strong-secret-key-change-this";

    public SecurityConfig(AuthService authService) {
        this.authService = authService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthFilter jwtFilter = new JwtAuthFilter(secret, authService);

        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF
            .headers(headers -> headers.frameOptions().disable()) // Allow H2 console
            .cors(cors -> {}) // Enable CORS
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable());

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Global CORS configuration
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000") // Replace with your frontend origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
