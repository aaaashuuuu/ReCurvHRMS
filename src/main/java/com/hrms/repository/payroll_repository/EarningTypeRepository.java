package com.hrms.repository.payroll_repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.entity.EarningType;

public interface EarningTypeRepository extends JpaRepository<EarningType, Integer> {

	Optional<EarningType> findByEarningTypeName(String name);
}
