package com.hrms.controller.payroll_controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.hrms.dto.payroll_dto.DeductionDTO;
import com.hrms.dto.payroll_dto.EarningDTO;
import com.hrms.dto.payroll_dto.EmployeeDeductionDTO;
import com.hrms.dto.payroll_dto.EmployeeEarningDTO;
import com.hrms.dto.payroll_dto.EmployeeSalaryDataDTO;
import com.hrms.entity.User;
import com.hrms.service.employee_service.IUserService;
import com.hrms.service.payroll_service.IDeductionService;
import com.hrms.service.payroll_service.IEarningService;
import com.hrms.service.payroll_service.IEmployeeSalaryService;

@Controller
@RequestMapping("payroll/employeeSalary")
public class EmployeeSalaryController {

    private IUserService userService;
    private IEmployeeSalaryService salaryService;
    private IEarningService earningService;
    private IDeductionService deductionService;

    public EmployeeSalaryController(IUserService userService, IEmployeeSalaryService salaryService,
                                    IEarningService earningService, IDeductionService deductionService) {
        this.userService = userService;
        this.salaryService = salaryService;
        this.earningService = earningService;
        this.deductionService = deductionService;
    }

    @GetMapping("/addEmployeeSalary")
    public String showForm(@RequestParam(value = "employeeId", required = false) Integer employeeId, Model model) {
        List<User> employeeList = userService.getAllUsers();
        model.addAttribute("employeeList", employeeList);

        if (employeeId != null) {
            User user = userService.getUserById(employeeId);
            model.addAttribute("employee", user);

            List<EarningDTO> earnings = earningService.findEarningsDTOByDepartmentAndDesignation(
                    user.getDepartment().getDepartmentId(), user.getDesignation().getDesignationId());
            List<DeductionDTO> deductions = deductionService.findDeductionsDTOByDepartmentAndDesignation(
                    user.getDepartment().getDepartmentId(), user.getDesignation().getDesignationId());

            model.addAttribute("earnings", earnings);
            model.addAttribute("deductions", deductions);
        } else {
            model.addAttribute("earnings", new ArrayList<>());
            model.addAttribute("deductions", new ArrayList<>());
        }

        model.addAttribute("content", "components/payroll/addEmployeeSalary");
      
        return "base-layout";
    }

    @PostMapping("/addEmployeeSalary")
    public String saveSalary(@RequestParam Integer employeeId, @RequestParam Double totalSalary, Model model) {
        User user = userService.getUserById(employeeId);

        List<EarningDTO> earnings = earningService.findEarningsDTOByDepartmentAndDesignation(
                user.getDepartment().getDepartmentId(), user.getDesignation().getDesignationId());
        List<DeductionDTO> deductions = deductionService.findDeductionsDTOByDepartmentAndDesignation(
                user.getDepartment().getDepartmentId(), user.getDesignation().getDesignationId());

        List<EmployeeEarningDTO> earningDTOs = new ArrayList<>();
        for (EarningDTO earning : earnings) {
            double amt = (totalSalary * earning.getPercentage()) / 100.0;
            earningDTOs.add(new EmployeeEarningDTO(earning.getId(), amt)); 
        }

        List<EmployeeDeductionDTO> deductionDTOs = new ArrayList<>();
        for (DeductionDTO deduction : deductions) {
            double amt = (totalSalary * deduction.getPercentage()) / 100.0;
            deductionDTOs.add(new EmployeeDeductionDTO(deduction.getId(), amt)); 
        }

        double totalEarnings = earningDTOs.stream().mapToDouble(EmployeeEarningDTO::getEarningAmt).sum();
        double totalDeductions = deductionDTOs.stream().mapToDouble(EmployeeDeductionDTO::getDeductionAmt).sum();
        double netSalary = totalSalary + totalEarnings - totalDeductions;

        salaryService.saveSalaryWithDetails(employeeId, totalSalary, netSalary, earningDTOs, deductionDTOs);

        model.addAttribute("employeeList", userService.getAllUsers());
        model.addAttribute("employee", user);
        model.addAttribute("earnings", earnings);
        model.addAttribute("deductions", deductions);
        model.addAttribute("totalSalary", totalSalary);
        model.addAttribute("netSalary", netSalary);

        return "redirect:/payroll/employeeSalary/addEmployeeSalary";
    }


    @GetMapping("/ajaxData")
    @ResponseBody
    public EmployeeSalaryDataDTO getSalaryData(@RequestParam Integer employeeId) {
        User user = userService.getUserById(employeeId);

        List<EarningDTO> earnings = earningService.findEarningsDTOByDepartmentAndDesignation(
                user.getDepartment().getDepartmentId(), user.getDesignation().getDesignationId());
        List<DeductionDTO> deductions = deductionService.findDeductionsDTOByDepartmentAndDesignation(
                user.getDepartment().getDepartmentId(), user.getDesignation().getDesignationId());

        EmployeeSalaryDataDTO dto = new EmployeeSalaryDataDTO();
        dto.setEarnings(earnings);
        dto.setDeductions(deductions);

        return dto;
    }
}
