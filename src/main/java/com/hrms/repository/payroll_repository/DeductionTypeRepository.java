package com.hrms.repository.payroll_repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrms.entity.DeductionType;
@Repository
public interface DeductionTypeRepository extends JpaRepository<DeductionType, Integer> {

	Optional<DeductionType> findByDeductionTypeName(String name);
}
