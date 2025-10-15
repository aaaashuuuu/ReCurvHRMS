package com.hrms.service.payroll_service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hrms.entity.EmployeeDeduction;
import com.hrms.repository.payroll_repository.EmployeeDeductionRepository;

@Service
public class EmployeeDeductionServiceImpl implements IEmployeeDeductionService {

    private final EmployeeDeductionRepository employeeDeductionRepository;

    public EmployeeDeductionServiceImpl(EmployeeDeductionRepository employeeDeductionRepository) {
        this.employeeDeductionRepository = employeeDeductionRepository;
    }

    @Override
    public List<EmployeeDeduction> findAllEmployeeDeduction() {
        return employeeDeductionRepository.findAll();
    }

    @Override
    public Optional<EmployeeDeduction> findById(Integer id) {
        return employeeDeductionRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        employeeDeductionRepository.deleteById(id);
    }

    @Override
    public EmployeeDeduction updateEmployeeDeduction(EmployeeDeduction employeeDeduction) {
        // check if exists
        if (employeeDeduction.getEmployeeDeductionId() == null || 
            !employeeDeductionRepository.existsById(employeeDeduction.getEmployeeDeductionId())) {
            throw new IllegalArgumentException("Employee Deduction not found for update");
        }
        return employeeDeductionRepository.save(employeeDeduction);
    }

    @Override
    public EmployeeDeduction saveEmployeeDeduction(EmployeeDeduction employeeDeduction) {
        return employeeDeductionRepository.save(employeeDeduction);
    }
}
