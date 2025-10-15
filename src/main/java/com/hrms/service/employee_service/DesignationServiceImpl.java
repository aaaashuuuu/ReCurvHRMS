package com.hrms.service.employee_service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.hrms.entity.Designation;
import com.hrms.repository.employee_repository.IDesignationRepository;
@Service
public class DesignationServiceImpl implements IDesignationService {
	
	private final IDesignationRepository designationRepository;

    public DesignationServiceImpl(IDesignationRepository designationRepository) {
        this.designationRepository = designationRepository;
    }

    @Override
    public List<Designation> getAllDesignations() {
        return designationRepository.findAll();
    }

    @Override
    public Designation getDesignationById(Integer id) {
        return designationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Designation not found with id: " + id));
    }

    @Override
    public Designation saveDesignation(Designation designation) {
        return designationRepository.save(designation);
    }

    @Override
    public void deleteDesignationById(Integer id) {
        designationRepository.deleteById(id);
    }

    @Override
    public List<Designation> getDesignationsByDepartment(Integer deptId) {
        return designationRepository.findByDepartmentDepartmentId(deptId);
    }
}
