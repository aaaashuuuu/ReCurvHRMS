package com.hrms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {
    private static final Logger logger = LoggerFactory.getLogger(PasswordUtil.class);
    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordUtil() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String encodePassword(String rawPassword) {
        String encoded = passwordEncoder.encode(rawPassword);
        logger.debug("🔐 Password encoding - Raw: [PROTECTED], Encoded: {}", encoded);
        return encoded;
    }

    public boolean matches(String rawPassword, String storedPassword) {
        logger.debug("🔐 Password matching check");
        logger.debug("Raw password provided: [PROTECTED]");
        logger.debug("Encoded password from DB: {}", storedPassword);

        if (storedPassword == null || storedPassword.trim().isEmpty()) {
            logger.debug("❌ Encoded password is NULL or EMPTY");
            return false;
        }

        // Remove {bcrypt} prefix if present
        String actualHash = storedPassword;
        if (storedPassword.startsWith("{bcrypt}")) {
            actualHash = storedPassword.substring(8); // Remove "{bcrypt}" prefix
            logger.debug("Removed {bcrypt} prefix, actual hash: {}", actualHash);
        }

        boolean matches = passwordEncoder.matches(rawPassword, actualHash);
        logger.debug("Password matches result: {}", matches);

        return matches;
    }
}