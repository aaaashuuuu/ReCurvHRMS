package com.hrms.service.payroll_service;


import java.util.List;

import org.springframework.stereotype.Service;


import com.hrms.dto.payroll_dto.EmployeeDeductionDTO;
import com.hrms.dto.payroll_dto.EmployeeEarningDTO;
import com.hrms.entity.EmpSalary;



@Service
public interface IEmployeeSalaryService {
	
	 EmpSalary saveSalaryWithDetails(Integer userId, Double totalSalary, Double netSalary,
			List<EmployeeEarningDTO> earningDTOs, List<EmployeeDeductionDTO> deductionDTOs);

}
