package com.click2vote.auth.service;

import com.click2vote.auth.repo.UserRepository;
import com.click2vote.common.domain.User;
import com.click2vote.auth.security.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.NoSuchElementException;

@Service
public class AuthService {

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Value("${jwt.secret}")
    private String secret;

    public AuthService(UserRepository repo) {
        this.repo = repo;
    }

    /** Register a new user and return a JWT */
    public String register(String username, String password, String displayName) {
        repo.findByUsername(username)
            .ifPresent(u -> { throw new IllegalArgumentException("Username already taken"); });

        User u = new User();
        u.setUsername(username);
        u.setPasswordHash(encoder.encode(password));
        u.setDisplayName(displayName);
        u.getRoles().add("ROLE_USER");

        repo.save(u);

        return JwtUtils.issue(
                u.getId().toString(),
                u.getRoles(),
                secret,
                Duration.ofHours(8).getSeconds()
        );
    }

    /** Login an existing user and return a JWT */
    public String login(String username, String password) {
        User u = repo.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Invalid username or password"));

        if (!encoder.matches(password, u.getPasswordHash())) {
            throw new SecurityException("Invalid username or password");
        }

        return JwtUtils.issue(
                u.getId().toString(),
                u.getRoles(),
                secret,
                Duration.ofHours(8).getSeconds()
        );
    }

    /** Load user by ID (needed for JwtAuthFilter) */
    public User loadUserById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
    }

    /** Load user by username (optional for integration with UserDetailsService) */
    public User loadUserByUsername(String username) {
        return repo.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found with username: " + username));
    }
}
