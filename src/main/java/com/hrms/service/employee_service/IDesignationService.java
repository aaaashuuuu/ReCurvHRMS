package com.hrms.service.employee_service;

import java.util.List;

import com.hrms.entity.Designation;

public interface IDesignationService {
	
		List<Designation> getAllDesignations();
	    Designation getDesignationById(Integer id);
	    Designation saveDesignation(Designation designation);
	    void deleteDesignationById(Integer id);
		List<Designation> getDesignationsByDepartment(Integer deptId);
}
