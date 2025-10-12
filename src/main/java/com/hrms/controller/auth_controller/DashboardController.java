package com.hrms.controller.auth_controller;

import com.hrms.entity.User;
import com.hrms.service.user_service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }

        String email = authentication.getName();
        String role = authentication.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");

        model.addAttribute("userEmail", email);
        model.addAttribute("userRole", role);
        model.addAttribute("pageTitle", "Dashboard - " + role);
        model.addAttribute("content", "components/dashboard-content");

        return "base-layout";
    }

    @GetMapping("/test-auth")
    public void testAuth(Authentication authentication){


        System.out.println(" ==============Printed from dashboard controller ========================");
        System.out.println(authentication.getPrincipal());
        String role = authentication.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");

        System.out.println(role);
        System.out.println("=============================================================");

        User user =new User();
//




    }






}