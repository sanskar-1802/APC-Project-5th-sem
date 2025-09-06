
package com.click2vote.auth.service;
import com.click2vote.auth.repo.UserRepository;
import com.click2vote.common.domain.User;
import com.click2vote.auth.security.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.*;
@Service
public class AuthService {
    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Value("${jwt.secret}") private String secret;
    public AuthService(UserRepository repo){ this.repo = repo; }
    public String register(String username, String password, String displayName){
        repo.findByUsername(username).ifPresent(u -> { throw new IllegalArgumentException("username taken"); });
        User u = new User();
        u.setUsername(username); u.setPasswordHash(encoder.encode(password)); u.setDisplayName(displayName);
        u.getRoles().add("ROLE_USER"); repo.save(u);
       return JwtUtils.issue(
        u.getId().toString(),
        u.getRoles(),
        secret,
        Duration.ofHours(8).getSeconds()
);

    }
    public String login(String username, String password){
        User u = repo.findByUsername(username).orElseThrow(() -> new NoSuchElementException("invalid"));
        if(!encoder.matches(password, u.getPasswordHash())) throw new SecurityException("invalid");
        return JwtUtils.issue(
        u.getId().toString(),
        u.getRoles(),
        secret,
        Duration.ofHours(8).getSeconds()
);

    }
}
