package com.hrms.repository.payroll_repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrms.entity.EarningType;
@Repository
public interface EarningTypeRepository extends JpaRepository<EarningType, Integer> {

	Optional<EarningType> findByEarningTypeName(String name);
}
