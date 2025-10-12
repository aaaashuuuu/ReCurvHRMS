package com.hrms.controller.auth_controller;

import com.hrms.dto.auth_dto.LoginRequest;
import com.hrms.dto.auth_dto.LoginResponse;
import com.hrms.entity.User;
import com.hrms.service.auth_service.IAuthService;
import com.hrms.service.user_service.IUserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;

//    public AuthController(IAuthService authService) {
//        this.authService = authService;
//    }

    private final IUserService userService;

    public AuthController(IAuthService authService, IUserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest,
                        HttpServletResponse response,
                        Model model) {

        LoginResponse loginResponse = authService.authenticateUser(loginRequest);

        if (loginResponse.isSuccess()) {
            // Create JWT token cookie
            Cookie jwtCookie = new Cookie("jwtToken", loginResponse.getToken());
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(false);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(24 * 60 * 60); // 24 hours
            response.addCookie(jwtCookie);

            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", loginResponse.getMessage());
            return "login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        // Clear JWT cookie
        Cookie jwtCookie = new Cookie("jwtToken", "");
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);
        response.addCookie(jwtCookie);

        return "redirect:/login?logout=true";
    }





    @GetMapping("/debug/password")
    @ResponseBody
    public String debugPassword(@RequestParam String email, @RequestParam String password) {
        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return String.format(
                    "Email: %s<br>Stored Hash: %s<br>Password Match: %s",
                    email,
                    user.getHashPassword(),
                    userService.validateUserCredentials(email, password)
            );
        }
        return "User not found: " + email;
    }

}