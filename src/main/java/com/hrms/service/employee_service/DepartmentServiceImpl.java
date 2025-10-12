package com.hrms.service.employee_service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hrms.entity.Department;
import com.hrms.repository.employee_repository.IDepartmentRepository;
@Service
public class DepartmentServiceImpl implements IDepartmentService {

	 private final IDepartmentRepository departmentRepository;

	    public DepartmentServiceImpl(IDepartmentRepository departmentRepository) {
	        this.departmentRepository = departmentRepository;
	    }

	    @Override
	    public List<Department> getAllDepartments() {
	        return departmentRepository.findAll();
	    }

	    @Override
	    public Department getDepartmentById(Integer id) {
	        return departmentRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
	    }

	    @Override
	    public Department saveDepartment(Department department) {
	        return departmentRepository.save(department);
	    }

	    @Override
	    public void deleteDepartmentById(Integer id) {
	        departmentRepository.deleteById(id);
	    }
}
