package com.hrms.service.payroll_service;

import com.hrms.dto.payroll_dto.DeductionDTO;
import com.hrms.entity.Deduction;
import com.hrms.entity.DeductionType;
import com.hrms.entity.Department;
import com.hrms.entity.Designation;
import com.hrms.entity.Earning;
import com.hrms.entity.EarningType;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public interface IDeductionService {
	   List<DeductionType> getAllDeductionTypes();
	    List<Deduction> getDeductionsByDepartmentAndDesignation(Integer departmentId, Integer designationId);
	    Deduction saveDeduction(Deduction deduction);
	    Deduction updateDeduction(Deduction deduction);
	    void deleteDeduction(Integer deductionId);
	    Optional<Deduction> getDeductionById(Integer deductionId);
	    List<Deduction> getAllDeductions();
	    
		public List<DeductionDTO> mapDeductionsToDTO(List<Deduction> allDeductions);
		
		Optional<Deduction> findByTypeDeptDesig(DeductionType type, Department dept, Designation desig);

}

