// package com.click2vote.auth.security;

// // import io.jsonwebtoken.*;
// import io.jsonwebtoken.security.Keys;
// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jws;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import java.security.Key;
// import java.time.Instant;
// import java.util.*;
// import java.util.stream.Collectors;

// public class JwtUtils {

//     public static String issue(String subject, Collection<String> roles, String secret, long seconds) {
//         if (secret.length() < 32) {
//             throw new IllegalArgumentException("Secret must be at least 32 characters for HS256");
//         }

//         Key key = Keys.hmacShaKeyFor(secret.getBytes());
//         return Jwts.builder()
//                 .setSubject(subject)
//                 .claim("roles", roles) // store as array
//                 .setIssuedAt(Date.from(Instant.now()))
//                 .setExpiration(Date.from(Instant.now().plusSeconds(seconds)))
//                 .signWith(key, SignatureAlgorithm.HS256)
//                 .compact();
//     }

//     public static Jws<Claims> parse(String token, String secret) {
//         Key key = Keys.hmacShaKeyFor(secret.getBytes());
//         return Jwts.parserBuilder()
//                 .setSigningKey(key)
//                 .build()
//                 .parseClaimsJws(token);
//     }

// public static List<String> extractRoles(Claims claims) {
//     Object roles = claims.get("roles");
//     if (roles instanceof Collection<?>) {
//         Collection<?> list = (Collection<?>) roles;
//         return list.stream().map(Object::toString).collect(Collectors.toList());
//     } else if (roles instanceof String) {
//         return Arrays.asList(((String) roles).split(","));
//     }
//     return Collections.emptyList();
// }


// }

package com.click2vote.auth.security;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.JwtException;

import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class JwtUtils {

    /** Issue a JWT token */
    public static String issue(String subject, Collection<String> roles, String secret, long seconds) {
        if (secret.length() < 32) {
            throw new IllegalArgumentException("Secret must be at least 32 characters for HS256");
        }

        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.builder()
                .setSubject(subject)
                .claim("roles", roles) // store roles as array
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(seconds)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /** Parse and validate JWT */
    public static Jws<Claims> parse(String token, String secret) {
        try {
            Key key = Keys.hmacShaKeyFor(secret.getBytes());
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException e) {
            throw new SecurityException("Invalid or expired JWT token", e);
        }
    }

    /** Extract user ID (subject) after validation */
    public static String validateAndGetUserId(String token, String secret) {
        Jws<Claims> jws = parse(token, secret);
        return jws.getBody().getSubject(); // userId was stored as subject
    }

    /** Extract roles from claims */
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

