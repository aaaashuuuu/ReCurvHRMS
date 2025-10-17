package com.hrms.repository.payroll_repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hrms.entity.Department;
import com.hrms.entity.Designation;
import com.hrms.entity.Earning;
import com.hrms.entity.EarningType;
@Repository
public interface EarningRepository extends JpaRepository<Earning, Integer> {

	List<Earning> findByDepartmentDepartmentIdAndDesignationDesignationId(Integer deptId, Integer desigId);

	List<Earning> findByEarningType_EarningTypeId(Integer earningTypeId);
	
	List<Earning> findByDepartmentDepartmentId(Integer departmentId);
	
	  @Query("SELECT e FROM Earning e WHERE e.earningType = :type AND e.department = :dept AND e.designation = :desig")
	    Optional<Earning> findByTypeDeptDesig(@Param("type") EarningType type,
	                                          @Param("dept") Department dept,
	                                          @Param("desig") Designation desig);
}
