package com.hrms.service.auth_service;

import com.hrms.dto.auth_dto.LoginRequest;
import com.hrms.dto.auth_dto.LoginResponse;
import com.hrms.entity.User;
import com.hrms.service.user_service.IUserService;
import com.hrms.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final IUserService userService;
    private final JwtUtil jwtUtil;

    public AuthService(IUserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        logger.debug("=== MANUAL LOGIN ATTEMPT ===");
        logger.debug("Email provided: {}", email);
        logger.debug("Password provided: [PROTECTED]");

        // Check if user exists
        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isEmpty()) {
            logger.debug("❌ User not found with email: {}", email);
            return new LoginResponse(false, "Invalid email or password");
        }

        User user = userOpt.get();
        logger.debug("✅ User found: {} {}", user.getFirstName(), user.getLastName());
        logger.debug("User status: {}", user.getStatus());
        logger.debug("User role: {}", user.getRole().getRoleName());
        logger.debug("Stored hash password: {}", user.getHashPassword());

        // Check if user is active
        if (!isUserActive(email)) {
            logger.debug("❌ User account is INACTIVE");
            return new LoginResponse(false, "User account is inactive");
        }

        // Validate credentials
        boolean isValidCredentials = userService.validateUserCredentials(email, password);
        logger.debug("Password validation result: {}", isValidCredentials);

        if (!isValidCredentials) {
            logger.debug("❌ Password validation FAILED");
            return new LoginResponse(false, "Invalid email or password");
        }

        // Generate JWT token
        Optional<com.hrms.dto.auth_dto.UserDetails> userDetailsOpt = userService.findUserDetailsByEmail(email);
        if (userDetailsOpt.isPresent()) {
            com.hrms.dto.auth_dto.UserDetails userDetails = userDetailsOpt.get();
            String token = jwtUtil.generateToken(userDetails.getEmail(), userDetails.getRole());

            logger.debug("✅ Login SUCCESSFUL - Generating JWT token");
            logger.debug("Token generated for: {} with role: {}", userDetails.getEmail(), userDetails.getRole());

            return new LoginResponse(token, userDetails.getEmail(), userDetails.getRole(),
                    userDetails.getFirstName(), userDetails.getLastName(), true);
        }

        logger.debug("❌ User details not found after validation");
        return new LoginResponse(false, "User details not found");
    }

    @Override
    public LoginResponse processOAuth2Login(String email) {
        logger.debug("=== OAUTH2 LOGIN ===");
        logger.debug("OAuth2 email: {}", email);

        // Check if user exists and is active
        if (!isUserActive(email)) {
            logger.debug("❌ OAuth2 user account is INACTIVE");
            return new LoginResponse(false, "User account is inactive");
        }

        // Generate JWT token for OAuth2 user
        Optional<com.hrms.dto.auth_dto.UserDetails> userDetailsOpt = userService.findUserDetailsByEmail(email);
        if (userDetailsOpt.isPresent()) {
            com.hrms.dto.auth_dto.UserDetails userDetails = userDetailsOpt.get();
            String token = jwtUtil.generateToken(userDetails.getEmail(), userDetails.getRole());

            logger.debug("✅ OAuth2 Login SUCCESSFUL");
            logger.debug("Token generated for: {} with role: {}", userDetails.getEmail(), userDetails.getRole());

            return new LoginResponse(token, userDetails.getEmail(), userDetails.getRole(),
                    userDetails.getFirstName(), userDetails.getLastName(), true);
        }

        logger.debug("❌ OAuth2 user not registered in system");
        return new LoginResponse(false, "User not registered in system");
    }

    @Override
    public boolean isUserActive(String email) {
        Optional<com.hrms.dto.auth_dto.UserDetails> userDetailsOpt = userService.findUserDetailsByEmail(email);
        boolean isActive = userDetailsOpt.isPresent() && "ACTIVE".equals(userDetailsOpt.get().getStatus());
        logger.debug("User active check for {}: {}", email, isActive);
        return isActive;
    }
}