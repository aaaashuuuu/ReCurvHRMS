package com.hrms.service.payroll_service;

import com.hrms.dto.payroll_dto.EarningDTO;
import com.hrms.entity.Department;
import com.hrms.entity.Designation;
import com.hrms.entity.Earning;
import com.hrms.entity.EarningType;
import com.hrms.repository.payroll_repository.EarningRepository;
import com.hrms.repository.payroll_repository.EarningTypeRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EarningServiceImpl implements IEarningService {

    private final EarningRepository earningRepository;
    private final EarningTypeRepository earningTypeRepository;

    public EarningServiceImpl(EarningRepository earningRepository, EarningTypeRepository earningTypeRepository) {
        this.earningRepository = earningRepository;
        this.earningTypeRepository = earningTypeRepository;
    }

    @Override
    public List<EarningType> getAllEarningTypes() {
        return earningTypeRepository.findAll();
    }

//    @Override
//    public List<Earning> getEarningsByDepartmentAndDesignation(Integer departmentId, Integer designationId) {
//        return earningRepository.findByDepartmentDepartmentIdAndDesignationDesignationId(departmentId, designationId);
//    }

    @Override
    public Earning saveEarning(Earning earning) {
        // Check if earning already exists for Type + Dept + Designation
        Optional<Earning> existing = earningRepository.findByTypeDeptDesig(
                earning.getEarningType(),
                earning.getDepartment(),
                earning.getDesignation()
        );

        if (existing.isPresent()) {
            
            Earning e = existing.get();
            e.setEarningPercentage(e.getEarningPercentage() + earning.getEarningPercentage());
            earningRepository.save(e);
        } else {
            earningRepository.save(earning);
        }
		return earning;
    }

    @Override
    public Earning updateEarning(Earning earning) {
        return earningRepository.save(earning);
    }

    @Override
    public void deleteEarning(Integer earningId) {
        earningRepository.deleteById(earningId);
    }

    @Override
    public Optional<Earning> getEarningById(Integer earningId) {
        return earningRepository.findById(earningId);
    }

    @Override
    public List<Earning> getAllEarnings() {
        return earningRepository.findAll();
    }

	@Override
	public List<EarningDTO> mapEarningsToDTO(List<Earning> earnings) {
	    return earnings.stream().map(e -> new EarningDTO(
	            e.getEarningId(),
	            e.getEarningType().getEarningTypeId(),
	            e.getEarningType().getEarningTypeName(),
	            e.getEarningPercentage(),
	            e.getDepartment().getDepartmentId(),
	            e.getDepartment().getDepartmentName(),
	            e.getDesignation().getDesignationId(),
	            e.getDesignation().getDesignationName()
	    )).toList();
	}

	@Override
	public Optional<Earning> findByTypeDeptDesig(EarningType type, Department dept, Designation desig) {
		return earningRepository.findByTypeDeptDesig(type, dept, desig);
	}

	@Override
	public List<Earning> findByDepartmentDepartmentIdAndDesignationDesignationId(Integer departmentId,
			Integer designationId) {
		// TODO Auto-generated method stub
		 return earningRepository.findByDepartmentDepartmentIdAndDesignationDesignationId(departmentId, designationId);
		   
	}
	
	  @Override
	    public List<EarningDTO> findEarningsDTOByDepartmentAndDesignation(Integer deptId, Integer desigId) {
	        List<Earning> earnings = earningRepository.findByDepartmentDepartmentIdAndDesignationDesignationId(deptId, desigId);
	        return earnings.stream().map(e -> new EarningDTO(
	                e.getEarningId(),
	                e.getEarningType().getEarningTypeId(),
	                e.getEarningType().getEarningTypeName(),
	                e.getEarningPercentage(),
	                e.getDepartment().getDepartmentId(),
	                e.getDepartment().getDepartmentName(),
	                e.getDesignation().getDesignationId(),
	                e.getDesignation().getDesignationName()))
	            .collect(Collectors.toList());
	    }


	 
}

