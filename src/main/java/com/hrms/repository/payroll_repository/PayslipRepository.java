package com.hrms.repository.payroll_repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrms.entity.Payslip;
import com.hrms.entity.User;
@Repository
public interface PayslipRepository extends JpaRepository<Payslip, Integer> {

	Page<Payslip> findByUser_UserId(Integer userId, Pageable pageable);
	
    Payslip findByUser_UserIdAndMonthAndYear(Integer userId, Integer month, Integer year);
    
    List<Payslip> findByUser_UserId(Integer userId);

  
  
}
