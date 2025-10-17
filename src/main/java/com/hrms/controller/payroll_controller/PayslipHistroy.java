package com.hrms.controller.payroll_controller;

import org.springframework.http.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import java.security.Principal;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hrms.entity.Payslip;
import com.hrms.entity.User;
import com.hrms.service.payroll_service.IPayslipService;
import com.hrms.service.user_service.IUserService;

import jakarta.servlet.http.HttpServletResponse;



@Controller
@RequestMapping("/payroll")
public class PayslipHistroy {

    private final IPayslipService payslipService;
    private final IUserService userService;

    public PayslipHistroy(IPayslipService payslipService, IUserService userService) {
        this.payslipService = payslipService;
        this.userService = userService;
    }

    @GetMapping("/payslipHistory")
    public String getPayslips(Model model, Authentication authentication) {
        List<Payslip> payslips;
        String role;

        if (authentication.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_MANAGER"))) {

            payslips = payslipService.getAllPayslips();
            role = "Admin"; // or assign Manager if you want a different display
        } else {
            String username = authentication.getName();
            User user = userService.findByEmail(username).orElse(null);
            if (user == null) {
                payslips = List.of();  // empty list if user not found
            } else {
                payslips = payslipService.getAllPayslipsByUserId(user.getUserId());
            }
            role = "EMPLOYEE";
        }

        model.addAttribute("payslipList", payslips);  // Ensure attribute name matches Thymeleaf forEach
        model.addAttribute("role", role);
        model.addAttribute("content", "components/payroll/payslip-history");
        return "base-layout";  // Ensure this is correct Thymeleaf template name
    }

    
    // View payslip PDF inline in browser
    @GetMapping("/PayslipDownload")
    public void viewPayslip(@RequestParam("payslipId") Integer payslipId, HttpServletResponse response) throws IOException {
        Payslip payslip = payslipService.getPayslipById(payslipId)
                            .orElseThrow(() -> new IOException("Payslip not found"));

        File file = new File(payslip.getPayslipPath());
        if (!file.exists()) {
            throw new IOException("File not found on server");
        }

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + file.getName());
        response.setContentLengthLong(file.length());

        try (FileInputStream in = new FileInputStream(file)) {
            in.transferTo(response.getOutputStream());
        }
    }

    // Download payslip PDF as attachment
    @GetMapping(value = "/PayslipDownload", params = "download")
    public void downloadPayslip(@RequestParam("payslipId") Integer payslipId, HttpServletResponse response) throws IOException {
        Payslip payslip = payslipService.getPayslipById(payslipId)
                            .orElseThrow(() -> new IOException("Payslip not found"));

        File file = new File(payslip.getPayslipPath());
        if (!file.exists()) {
            throw new IOException("File not found on server");
        }

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        response.setContentLengthLong(file.length());

        try (FileInputStream in = new FileInputStream(file)) {
            in.transferTo(response.getOutputStream());
        }
    }
}
