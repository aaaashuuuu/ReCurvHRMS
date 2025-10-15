package com.hrms.service.payroll_service;

import com.hrms.dto.payroll_dto.DeductionDTO;
import com.hrms.entity.Deduction;
import com.hrms.entity.DeductionType;
import com.hrms.entity.Department;
import com.hrms.entity.Designation;
import com.hrms.repository.payroll_repository.DeductionRepository;
import com.hrms.repository.payroll_repository.DeductionTypeRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeductionServiceImpl implements IDeductionService {

	private final DeductionRepository deductionRepository;
	private final DeductionTypeRepository deductionTypeRepository;

	public DeductionServiceImpl(DeductionRepository deductionRepository, DeductionTypeRepository deductionTypeRepository) {
		this.deductionRepository = deductionRepository;
		this.deductionTypeRepository = deductionTypeRepository;
	}

	@Override
	public List<DeductionType> getAllDeductionTypes() {
		return deductionTypeRepository.findAll();
	}

	@Override
	public List<Deduction> getDeductionsByDepartmentAndDesignation(Integer departmentId, Integer designationId) {
		return deductionRepository.findByDepartmentDepartmentIdAndDesignationDesignationId(departmentId, designationId);
	}

    @Override
    public Deduction saveDeduction(Deduction deduction) {
        // Check if deduction already exists for Type + Dept + Designation
        Optional<Deduction> existing = deductionRepository.findByTypeDeptDesig(
                deduction.getDeductionType(),
                deduction.getDepartment(),
                deduction.getDesignation()
        );

        if (existing.isPresent()) {
            Deduction d = existing.get();
            d.setDeductionPercentage(d.getDeductionPercentage() + deduction.getDeductionPercentage());
            deductionRepository.save(d);
        } else {
            deductionRepository.save(deduction);
        }
        return deduction;
    }

	@Override
	public Deduction updateDeduction(Deduction deduction) {
		return deductionRepository.save(deduction);
	}

	@Override
	public void deleteDeduction(Integer deductionId) {
		deductionRepository.deleteById(deductionId);
	}

	@Override
	public Optional<Deduction> getDeductionById(Integer deductionId) {
		return deductionRepository.findById(deductionId);
	}

	@Override
	public List<Deduction> getAllDeductions() {
		return deductionRepository.findAll();
	}

	@Override
	public List<DeductionDTO> mapDeductionsToDTO(List<Deduction> deductions) {
	    return deductions.stream()
	        .map(d -> new DeductionDTO(
	            d.getDeductionId(),
	            d.getDeductionType().getDeductionTypeId(),
	            d.getDeductionType().getDeductionTypeName(),
	            d.getDeductionPercentage(),
	            d.getDepartment().getDepartmentId(),
	            d.getDepartment().getDepartmentName(),
	            d.getDesignation().getDesignationId(),
	            d.getDesignation().getDesignationName()
	        ))
	        .toList();
	}

	@Override
	public Optional<Deduction> findByTypeDeptDesig(DeductionType type, Department dept, Designation desig) {
		// TODO Auto-generated method stub
		return deductionRepository.findByTypeDeptDesig(type, dept, desig);
	}


}
