package com.hrms.repository.payroll_repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hrms.entity.EmpSalary;
@Repository
public interface EmployeeSalaryRepository extends JpaRepository<EmpSalary, Integer>{

	 // list salary history for a user
    Page<EmpSalary> findByUser_UserId(Integer userId, Pageable pageable);

    // find latest salary entries (by createdDate)
    Page<EmpSalary> findAllByOrderByCreatedDateDesc(Pageable pageable);

    @Query("SELECT e FROM EmpSalary e " +
            "WHERE e.user.userId = :userId " +
            "AND FUNCTION('MONTH', e.createdDate) = :month " +
            "AND FUNCTION('YEAR', e.createdDate) = :year")
     Optional<EmpSalary> findByUser_UserIdAndCreatedDateMonthAndYear(
             @Param("userId") int userId,
             @Param("month") int month,
             @Param("year") int year);
    
    Optional<EmpSalary> findTopByUser_UserIdOrderByCreatedDateDesc(Integer userId);
}
