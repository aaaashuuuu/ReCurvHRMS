package com.hrms.service.payroll_service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hrms.repository.payroll_repository.*;
import com.hrms.repository.employee_repository.*;
import com.hrms.entity.*;

@Service
public class SalaryConfigService {

    private final EarningRepository earningRepo;
    private final DeductionRepository deductionRepo;
    private final EarningTypeRepository earningTypeRepo;
    private final DeductionTypeRepository deductionTypeRepo;
    private final IDepartmentRepository departmentRepo;
    private final IDesignationRepository designationRepo;

    public SalaryConfigService(EarningRepository earningRepo, DeductionRepository deductionRepo,
                               EarningTypeRepository earningTypeRepo, DeductionTypeRepository deductionTypeRepo,
                               IDepartmentRepository departmentRepo, IDesignationRepository designationRepo) {
        this.earningRepo = earningRepo;
        this.deductionRepo = deductionRepo;
        this.earningTypeRepo = earningTypeRepo;
        this.deductionTypeRepo = deductionTypeRepo;
        this.departmentRepo = departmentRepo;
        this.designationRepo = designationRepo;
    }

    // ---------- Departments & Designations ----------
    public List<Department> getAllDepartments() {
        return departmentRepo.findAll();
    }

    public List<Designation> getDesignationsByDept(Integer deptId) {
        return designationRepo.findByDepartmentDepartmentId(deptId);
    }

    // ---------- Earning ----------
    public List<Earning> getAllEarnings() {
        return earningRepo.findAll();
    }

    public Earning saveEarning(Earning earning) {
        return earningRepo.save(earning);
    }

    public Earning getEarningById(Integer id) {
        return earningRepo.findById(id).orElse(null);
    }

    public void deleteEarning(Integer id) {
        earningRepo.deleteById(id);
    }

    // ---------- Deduction ----------
    public List<Deduction> getAllDeductions() {
        return deductionRepo.findAll();
    }

    public Deduction saveDeduction(Deduction deduction) {
        return deductionRepo.save(deduction);
    }

    public Deduction getDeductionById(Integer id) {
        return deductionRepo.findById(id).orElse(null);
    }

    public void deleteDeduction(Integer id) {
        deductionRepo.deleteById(id);
    }

    // ---------- Types ----------
    public List<EarningType> getAllEarningTypes() {
        return earningTypeRepo.findAll();
    }

    public List<DeductionType> getAllDeductionTypes() {
        return deductionTypeRepo.findAll();
    }

    public EarningType getEarningTypeById(Integer id) {
        return earningTypeRepo.findById(id).orElse(null);
    }

    public DeductionType getDeductionTypeById(Integer id) {
        return deductionTypeRepo.findById(id).orElse(null);
    }

    public Department getDepartmentById(Integer id) {
        return departmentRepo.findById(id).orElse(null);
    }

    public Designation getDesignationById(Integer id) {
        return designationRepo.findById(id).orElse(null);
    }
}

