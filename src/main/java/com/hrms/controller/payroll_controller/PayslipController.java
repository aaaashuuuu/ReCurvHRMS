package com.hrms.controller.payroll_controller;


	import com.hrms.entity.User;
	import com.hrms.service.employee_service.IUserService;
	import com.hrms.service.payroll_service.IPayslipService;
	import com.hrms.dto.payroll_dto.PayslipRequestDTO;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.format.annotation.DateTimeFormat;
	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.web.bind.annotation.*;

	import java.time.Year;

	@Controller
	@RequestMapping("/payroll")
	public class PayslipController {

	    @Autowired
	    private IUserService userService;

	    @Autowired
	    private IPayslipService payslipService;

	    @GetMapping("/generatePayslip")
	    public String showPayslipForm(Model model) {
	        model.addAttribute("employeeList", userService.getAllUsers());
	        model.addAttribute("payslipRequest", new PayslipRequestDTO());
	        model.addAttribute("currentYear", Year.now().getValue());
	        model.addAttribute("content", "components/payroll/generatePayslip");
	        return "base-layout";
	    }

	    @PostMapping("/generatePayslip")
	    public String generatePayslip(@ModelAttribute PayslipRequestDTO payslipRequest, Model model) {
	        try {
	            String pdfPath = payslipService.generateMonthlyPayslip(
	                    payslipRequest.getEmployeeId(),
	                    payslipRequest.getMonth(),
	                    payslipRequest.getYear());

	            model.addAttribute("message", "Payslip generated successfully!");
	            model.addAttribute("payslipPath", pdfPath);
	        } catch (Exception e) {
	            model.addAttribute("error", "Failed to generate payslip: " + e.getMessage());
	        }
	        model.addAttribute("employeeList", userService.getAllUsers());
	        model.addAttribute("payslipRequest", payslipRequest);
	        model.addAttribute("currentYear", Year.now().getValue());
	        model.addAttribute("content", "components/payroll/generatePayslip");
	        return "base-layout";
	    }
	}


