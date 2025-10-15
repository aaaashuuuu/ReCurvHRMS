package com.hrms.repository.payroll_repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.entity.Payslip;

public interface PayslipRepository extends JpaRepository<Payslip, Integer> {

	Page<Payslip> findByUser_UserId(Integer userId, Pageable pageable);

    // find payslip for a month/year for a user
    Payslip findByUser_UserIdAndMonthAndYear(Integer userId, Integer month, Integer year);
}
