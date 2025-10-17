package com.hrms.service.payroll_service;

import com.hrms.dto.payroll_dto.EarningDTO;
import com.hrms.entity.Deduction;
import com.hrms.entity.Department;
import com.hrms.entity.Designation;
import com.hrms.entity.Earning;
import com.hrms.entity.EarningType;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public interface IEarningService {
	List<EarningType> getAllEarningTypes();

//	List<Earning> getEarningsByDepartmentAndDesignation(Integer departmentId, Integer designationId);

	Earning saveEarning(Earning earning);

	Earning updateEarning(Earning earning);

	void deleteEarning(Integer earningId);

	Optional<Earning> getEarningById(Integer earningId);

	List<Earning> getAllEarnings();

	public List<EarningDTO> mapEarningsToDTO(List<Earning> earnings);

	Optional<Earning> findByTypeDeptDesig(EarningType type, Department dept, Designation desig);
	
	List<Earning> findByDepartmentDepartmentIdAndDesignationDesignationId(Integer departmentId,Integer designationId);

	List<EarningDTO> findEarningsDTOByDepartmentAndDesignation(Integer deptId, Integer desigId);


}
