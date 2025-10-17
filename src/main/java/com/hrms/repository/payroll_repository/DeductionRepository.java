package com.hrms.repository.payroll_repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hrms.dto.payroll_dto.DeductionDTO;
import com.hrms.entity.Deduction;
import com.hrms.entity.DeductionType;
import com.hrms.entity.Department;
import com.hrms.entity.Designation;
@Repository
public interface DeductionRepository extends JpaRepository<Deduction, Integer> {

	List<Deduction> findByDepartmentDepartmentIdAndDesignationDesignationId(Integer deptId, Integer desigId);

	
	List<Deduction> findByDeductionType_DeductionTypeId(Integer deductionTypeId);
	
	List<Deduction> findByDepartmentDepartmentId(Integer departmentId);
	
	@Query("SELECT d FROM Deduction d WHERE d.deductionType = :type AND d.department = :dept AND d.designation = :desig")
    Optional<Deduction> findByTypeDeptDesig(@Param("type") DeductionType type,
                                            @Param("dept") Department dept,
                                            @Param("desig") Designation desig);


//	List<DeductionDTO> mapDeductionsToDTO(List<Deduction> deductions);
}
