package com.hrms.repository.payroll_repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrms.entity.EmployeeEarning;
@Repository
public interface EmployeeEarningRepository extends JpaRepository<EmployeeEarning, Integer> {

	List<EmployeeEarning> findByUser_UserId(Integer userId);

    List<EmployeeEarning> findByEmpSalary_SalaryId(Integer salaryId);

    List<EmployeeEarning> findByUser_UserIdAndEmpSalary_SalaryId(Integer userId, Integer salaryId);

}
