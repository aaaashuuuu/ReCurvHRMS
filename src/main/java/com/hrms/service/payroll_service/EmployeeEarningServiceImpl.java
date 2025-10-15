package com.hrms.service.payroll_service;

import java.util.List;

import com.hrms.entity.EmployeeEarning;
import com.hrms.repository.payroll_repository.EmployeeEarningRepository;

public class EmployeeEarningServiceImpl implements IEmployeeEarningService {

	EmployeeEarningRepository employeeEarningRepository;

	public EmployeeEarningServiceImpl(EmployeeEarningRepository employeeEarningRepository) {
		super();
		this.employeeEarningRepository = employeeEarningRepository;
	}

	public List<EmployeeEarning> findAllEmployeeEarning() {
		return employeeEarningRepository.findAll();
	}
	
	public EmployeeEarning findById(Integer id) {
		return employeeEarningRepository.findById(id).get();
	}
	
	public void saveEmployeeEarning(EmployeeEarning employeeEarning) {
		employeeEarningRepository.save(employeeEarning);
	}
	
	public void deleteEmployeeEarningById(Integer id) {
		employeeEarningRepository.deleteById(id);
	}
	
	public EmployeeEarning updateEmployeeEarning(EmployeeEarning employeeEarning) {
		return null;
	}

}
