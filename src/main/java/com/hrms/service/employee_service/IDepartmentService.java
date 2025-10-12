package com.hrms.service.employee_service;

import java.util.List;

import com.hrms.entity.Department;

public interface IDepartmentService {
	
	 	List<Department> getAllDepartments();
	    Department getDepartmentById(Integer id);
	    Department saveDepartment(Department department);
	    void deleteDepartmentById(Integer id);
}	
