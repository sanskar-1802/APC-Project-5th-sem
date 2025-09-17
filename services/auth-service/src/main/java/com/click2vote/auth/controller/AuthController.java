package com.click2vote.auth.controller;

import com.click2vote.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /** DTO for register */
    public static class RegisterRequest {
        public String username;
        public String password;
        public String displayName;
    }

    /** DTO for login */
    public static class LoginRequest {
        public String username;
        public String password;
    }

    /** Register endpoint */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        String token = authService.register(req.username, req.password, req.displayName);
        return ResponseEntity.ok(Map.of("token", token));
    }

    /** Login endpoint */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        String token = authService.login(req.username, req.password);
        return ResponseEntity.ok(Map.of("token", token));
    }

    /** Just to test authentication with JWT */
    @GetMapping("/me")
    public ResponseEntity<?> me() {
        return ResponseEntity.ok(Map.of("message", "You are authenticated ✅"));
    }
}
