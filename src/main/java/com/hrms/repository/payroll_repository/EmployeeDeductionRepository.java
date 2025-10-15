package com.hrms.repository.payroll_repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.entity.EmployeeDeduction;

public interface EmployeeDeductionRepository extends JpaRepository<EmployeeDeduction, Integer>{

	 List<EmployeeDeduction> findByUser_UserId(Integer userId);

	    List<EmployeeDeduction> findByEmpSalary_SalaryId(Integer salaryId);

	    List<EmployeeDeduction> findByUser_UserIdAndEmpSalary_SalaryId(Integer userId, Integer salaryId);
}
