package com.hrms.dto.payroll_dto;

import java.util.List;

public class EmployeeSalaryDataDTO {
    private List<EarningDTO> earnings;
    private List<DeductionDTO> deductions;

    public List<EarningDTO> getEarnings() {
        return earnings;
    }

    public void setEarnings(List<EarningDTO> earnings) {
        this.earnings = earnings;
    }

    public List<DeductionDTO> getDeductions() {
        return deductions;
    }

    public void setDeductions(List<DeductionDTO> deductions) {
        this.deductions = deductions;
    }
}
