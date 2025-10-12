package com.hrms.security;

import com.hrms.dto.auth_dto.LoginResponse;
import com.hrms.service.auth_service.IAuthService;
import com.hrms.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(OAuth2SuccessHandler.class);
    private final IAuthService authService;
    private final JwtUtil jwtUtil;

    public OAuth2SuccessHandler(IAuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        logger.debug("OAuth2 authentication successful");

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauthUser = oauthToken.getPrincipal();

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        logger.debug("OAuth2 user - Email: {}, Name: {}", email, name);

        if (email == null) {
            logger.error("OAuth2 email not found in user attributes");
            response.sendRedirect("/login?error=oauth_email_not_found");
            return;
        }

        // Process OAuth2 login
        LoginResponse loginResponse = authService.processOAuth2Login(email);

        if (loginResponse.isSuccess()) {
            logger.debug("OAuth2 login successful for user: {}", email);

            // Create JWT token cookie
            Cookie jwtCookie = new Cookie("jwtToken", loginResponse.getToken());
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(false); // Set to true in production with HTTPS
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(24 * 60 * 60); // 24 hours
            response.addCookie(jwtCookie);

            // Redirect to simple dashboard (remove role-specific redirects)
            logger.debug("Redirecting OAuth2 user to dashboard");
            response.sendRedirect("/dashboard");
        } else {
            logger.warn("OAuth2 login failed for user: {} - {}", email, loginResponse.getMessage());
            response.sendRedirect("/login?error=oauth_user_not_found");
        }
    }


}