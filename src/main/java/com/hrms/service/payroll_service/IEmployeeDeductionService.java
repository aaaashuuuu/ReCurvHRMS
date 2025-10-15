package com.hrms.service.payroll_service;

import java.util.List;
import java.util.Optional;

import com.hrms.entity.EmployeeDeduction;

public interface IEmployeeDeductionService {
	
	List<EmployeeDeduction> findAllEmployeeDeduction();
	
	Optional<EmployeeDeduction> findById(Integer id);
	
	void deleteById(Integer id);
	
	EmployeeDeduction updateEmployeeDeduction(EmployeeDeduction employeeDeduction);

	EmployeeDeduction saveEmployeeDeduction(EmployeeDeduction employeeDeduction);

}
