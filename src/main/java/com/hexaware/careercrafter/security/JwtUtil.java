package com.hexaware.careercrafter.security;

import java.util.Base64;
import java.util.Date;
import java.nio.charset.StandardCharsets;
import jakarta.annotation.PostConstruct;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * 
 * Author: Chandru
 * Date: 13-Aug-2025
 * 
 * 
 */

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key key;

    @PostConstruct
    public void init() {
        try {
            try {
                byte[] decodedKey = Base64.getDecoder().decode(secret.trim());
                this.key = Keys.hmacShaKeyFor(decodedKey);
                logger.info("JWT secret successfully initialized as Base64 encoded key");
            } catch (IllegalArgumentException e) {
                logger.warn("JWT secret is not Base64 encoded - using raw string (less secure)");
                byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);

                if (keyBytes.length < 32) {
                    byte[] paddedKey = new byte[32];
                    System.arraycopy(keyBytes, 0, paddedKey, 0, Math.min(keyBytes.length, 32));
                    this.key = Keys.hmacShaKeyFor(paddedKey);
                } else {
                    this.key = Keys.hmacShaKeyFor(keyBytes);
                }
            }

            // test token
            generateToken("test", "employer");
            logger.debug("JWT initialization test successful");

        } catch (Exception e) {
            logger.error("Failed to initialize JWT secret", e);
            throw new IllegalStateException("JWT initialization failed", e);
        }
    }

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role) // still supports single role
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) throws JwtException {
        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) throws JwtException {
        return extractAllClaims(token).get("role", String.class);
    }

    // ✅ NEW METHOD to support list of roles (without ROLE_ prefix)
    public List<String> extractRoles(String token) throws JwtException {
        Claims claims = extractAllClaims(token);

        // single role case (what you have now)
        String role = claims.get("role", String.class);
        if (role != null) {
            return Collections.singletonList(role);
        }

        // if later you add multiple roles
        List<String> roles = claims.get("roles", List.class);
        return roles != null ? roles : Collections.emptyList();
    }

    public boolean validateToken(String token, String username) {
        try {
            final String extractedUsername = extractUsername(token);
            return (extractedUsername.equals(username) && !isTokenExpired(token));
        } catch (JwtException | IllegalArgumentException e) {
            logger.warn("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    private boolean isTokenExpired(String token) throws JwtException {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) throws JwtException {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            logger.warn("JWT token expired: {}", e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            logger.warn("Unsupported JWT token: {}", e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            logger.warn("Malformed JWT token: {}", e.getMessage());
            throw e;
        } catch (SignatureException e) {
            logger.warn("Invalid JWT signature: {}", e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            logger.warn("JWT claims string is empty: {}", e.getMessage());
            throw e;
        }
    }
}
