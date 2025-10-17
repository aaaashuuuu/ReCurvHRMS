package com.hrms.service.payroll_service;

import java.util.List;
import java.util.Optional;

import com.hrms.entity.Payslip;


public interface IPayslipService {
    String generateMonthlyPayslip(int userId, int month, int year) throws Exception;
    
    List<Payslip> getAllPayslips();

	List<Payslip> getAllPayslipsByUserId(Integer userId);

	Optional<Payslip> getPayslipById(Integer payslipId);
	
	

}
