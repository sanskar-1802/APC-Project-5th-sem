
package com.click2vote.auth.web;
import com.click2vote.auth.service.AuthService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService svc;
    public AuthController(AuthService svc){ this.svc = svc; }
    @PostMapping("/register") public Map<String,String> register(@RequestBody Map<String,String> body){
        String token = svc.register(body.get("username"), body.get("password"), body.getOrDefault("displayName", ""));
        return Map.of("token", token);
    }
    @PostMapping("/login") public Map<String,String> login(@RequestBody Map<String,String> body){
        String token = svc.login(body.get("username"), body.get("password"));
        return Map.of("token", token);
    }
}
