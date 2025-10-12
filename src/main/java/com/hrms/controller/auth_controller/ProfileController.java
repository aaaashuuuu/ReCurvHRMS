package com.hrms.controller.auth_controller;

import com.hrms.service.user_service.IUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class ProfileController {

    private final IUserService userService;

    public ProfileController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profilePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        String email = authentication.getName();

        // Get user details from service
        Optional<com.hrms.dto.auth_dto.UserDetails> userDetailsOpt = userService.findUserDetailsByEmail(email);

        if (userDetailsOpt.isPresent()) {
            com.hrms.dto.auth_dto.UserDetails userDetails = userDetailsOpt.get();
            model.addAttribute("userDetails", userDetails);
        } else {
            // Fallback if user details not found
            model.addAttribute("userEmail", email);
        }

        // NEW: Set layout attributes
        model.addAttribute("content", "components/profile-content");
        model.addAttribute("pageTitle", "My Profile");

        return "base-layout"; // ← CHANGED from "profile" to "base-layout"
    }
}