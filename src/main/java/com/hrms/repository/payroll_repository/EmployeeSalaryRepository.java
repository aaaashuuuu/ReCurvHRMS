package com.hrms.repository.payroll_repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.entity.EmpSalary;

public interface EmployeeSalaryRepository extends JpaRepository<EmpSalary, Integer>{

	 // list salary history for a user
    Page<EmpSalary> findByUser_UserId(Integer userId, Pageable pageable);

    // find latest salary entries (by createdDate)
    Page<EmpSalary> findAllByOrderByCreatedDateDesc(Pageable pageable);
}
