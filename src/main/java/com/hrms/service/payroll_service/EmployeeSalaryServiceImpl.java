package com.hrms.service.payroll_service;


import com.hrms.dto.payroll_dto.DeductionDTO;
import com.hrms.dto.payroll_dto.EarningDTO;
import com.hrms.dto.payroll_dto.EmployeeDeductionDTO;
import com.hrms.dto.payroll_dto.EmployeeEarningDTO;
import com.hrms.entity.*;
import com.hrms.repository.*;
import com.hrms.repository.employee_repository.IUserRepository;
import com.hrms.repository.payroll_repository.DeductionRepository;
import com.hrms.repository.payroll_repository.EarningRepository;
import com.hrms.repository.payroll_repository.EmployeeDeductionRepository;
import com.hrms.repository.payroll_repository.EmployeeEarningRepository;
import com.hrms.repository.payroll_repository.EmployeeSalaryRepository;
import com.hrms.service.employee_service.IUserService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class EmployeeSalaryServiceImpl implements IEmployeeSalaryService {

   
    private EmployeeSalaryRepository empSalaryRepository;

   
    private EmployeeDeductionRepository employeeDeductionRepository;

   
    private EmployeeEarningRepository employeeEarningRepository;
    
    
    private IUserService userService;
    
    private EarningRepository earningRepository;
    
    private DeductionRepository deductionRepository;
    
    private IDeductionService deductionService;
    
    private IEarningService earningService;

    

 

	public EmployeeSalaryServiceImpl(EmployeeSalaryRepository empSalaryRepository,
			EmployeeDeductionRepository employeeDeductionRepository,
			EmployeeEarningRepository employeeEarningRepository, IUserService userService,
			EarningRepository earningRepository, DeductionRepository deductionRepository,
			IDeductionService deductionService, IEarningService earningService) {
		super();
		this.empSalaryRepository = empSalaryRepository;
		this.employeeDeductionRepository = employeeDeductionRepository;
		this.employeeEarningRepository = employeeEarningRepository;
		this.userService = userService;
		this.earningRepository = earningRepository;
		this.deductionRepository = deductionRepository;
		this.deductionService = deductionService;
		this.earningService = earningService;
	}

	public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public User getUserById(Integer userId) {
        return userService.getUserById(userId);
    }

    public List<Deduction> getDeductionsByDeptAndDesignation(Integer deptId, Integer desigId) {
        return deductionService.findByDepartmentDepartmentIdAndDesignationDesignationId(deptId, desigId);
    }

    public List<Earning> getEarningsByDepartmentAndDesignation(Integer deptId, Integer desigId){
        return earningService.findByDepartmentDepartmentIdAndDesignationDesignationId(deptId, desigId);
    }

    @Transactional
    @Override
    public EmpSalary saveSalaryWithDetails(Integer userId, Double totalSalary, Double netSalary,
                                           List<EmployeeEarningDTO> earningDTOs,
                                           List<EmployeeDeductionDTO> deductionDTOs) {

        User user = getUserById(userId);

        EmpSalary empSalary = new EmpSalary();
        empSalary.setUser(user);
        empSalary.setTotalSalary(totalSalary);
        empSalary.setNetSalary(netSalary);
        empSalary.setCreatedDate(LocalDateTime.now());

        empSalary = empSalaryRepository.save(empSalary);

        // Save each earning
        for (EmployeeEarningDTO dto : earningDTOs) {
            EmployeeEarning employeeEarning = new EmployeeEarning();
            employeeEarning.setUser(user);
            employeeEarning.setEmpSalary(empSalary);

            Earning earningEntity = earningRepository.findById(dto.getEarningId())
                    .orElseThrow(() -> new RuntimeException("Earning not found: " + dto.getEarningId()));
            employeeEarning.setEarning(earningEntity);

            employeeEarning.setEarningAmt(dto.getEarningAmt());
            employeeEarningRepository.save(employeeEarning);
        }

        // Save each deduction
        for (EmployeeDeductionDTO dto : deductionDTOs) {
            EmployeeDeduction employeeDeduction = new EmployeeDeduction();
            employeeDeduction.setUser(user);
            employeeDeduction.setEmpSalary(empSalary);

            Deduction deductionEntity = deductionRepository.findById(dto.getDeductionId())
                    .orElseThrow(() -> new RuntimeException("Deduction not found: " + dto.getDeductionId()));
            employeeDeduction.setDeduction(deductionEntity);

            employeeDeduction.setDeductionAmt(dto.getDeductionAmt());
            employeeDeductionRepository.save(employeeDeduction);
        }

        return empSalary;
    }


}
