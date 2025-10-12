package com.hrms.util;

import com.hrms.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
        // Validate that secret is not null or empty
        if (jwtConfig.getSecret() == null || jwtConfig.getSecret().trim().isEmpty()) {
            throw new IllegalArgumentException("JWT secret cannot be null or empty");
        }
        this.secretKey = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
    }

    public String generateToken(String email, String role) {
        logger.debug("Generating JWT token for email: {}, role: {}", email, role);

        try {
            String token = Jwts.builder()
                    .setSubject(email)
                    .claim("role", role)
                    .setIssuer(jwtConfig.getIssuer())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                    .signWith(secretKey)
                    .compact();

            logger.debug("JWT token generated successfully for email: {}", email);
            return token;
        } catch (Exception e) {
            logger.error("Error generating JWT token for email: {}", email, e);
            throw new RuntimeException("Failed to generate JWT token", e);
        }
    }

    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            logger.error("Error extracting claims from JWT token", e);
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    public String extractEmail(String token) {
        try {
            return extractAllClaims(token).getSubject();
        } catch (Exception e) {
            logger.error("Error extracting email from JWT token", e);
            return null;
        }
    }

    public String extractRole(String token) {
        try {
            return extractAllClaims(token).get("role", String.class);
        } catch (Exception e) {
            logger.error("Error extracting role from JWT token", e);
            return null;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            return extractAllClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            logger.error("Error checking token expiration", e);
            return true; // Consider invalid if we can't check expiration
        }
    }

    public boolean validateToken(String token, String email) {
        try {
            String extractedEmail = extractEmail(token);
            boolean emailMatches = email.equals(extractedEmail);
            boolean notExpired = !isTokenExpired(token);

            logger.debug("Token validation - Email matches: {}, Not expired: {}", emailMatches, notExpired);

            return emailMatches && notExpired;
        } catch (Exception e) {
            logger.error("Error validating JWT token for email: {}", email, e);
            return false;
        }
    }

    public Date getExpirationDate(String token) {
        try {
            return extractAllClaims(token).getExpiration();
        } catch (Exception e) {
            logger.error("Error getting expiration date from JWT token", e);
            return null;
        }
    }

    public Date getIssuedAtDate(String token) {
        try {
            return extractAllClaims(token).getIssuedAt();
        } catch (Exception e) {
            logger.error("Error getting issued at date from JWT token", e);
            return null;
        }
    }
}