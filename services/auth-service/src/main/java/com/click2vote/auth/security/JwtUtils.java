package com.click2vote.auth.security;

// import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class JwtUtils {

    public static String issue(String subject, Collection<String> roles, String secret, long seconds) {
        if (secret.length() < 32) {
            throw new IllegalArgumentException("Secret must be at least 32 characters for HS256");
        }

        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.builder()
                .setSubject(subject)
                .claim("roles", roles) // store as array
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(seconds)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public static Jws<Claims> parse(String token, String secret) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

public static List<String> extractRoles(Claims claims) {
    Object roles = claims.get("roles");
    if (roles instanceof Collection<?>) {
        Collection<?> list = (Collection<?>) roles;
        return list.stream().map(Object::toString).collect(Collectors.toList());
    } else if (roles instanceof String) {
        return Arrays.asList(((String) roles).split(","));
    }
    return Collections.emptyList();
}


}
