package com.hrms.repository.payroll_repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrms.entity.EmployeeDeduction;
@Repository
public interface EmployeeDeductionRepository extends JpaRepository<EmployeeDeduction, Integer>{

	 List<EmployeeDeduction> findByUser_UserId(Integer userId);

	    List<EmployeeDeduction> findByEmpSalary_SalaryId(Integer salaryId);

	    List<EmployeeDeduction> findByUser_UserIdAndEmpSalary_SalaryId(Integer userId, Integer salaryId);
}
