package com.hrms.dto.payroll_dto;

public class EmployeeDeductionDTO {
    private Integer deductionId;      // Deduction entity ID
    private Double deductionAmt;      // Calculated amount

    public EmployeeDeductionDTO() {}

    public EmployeeDeductionDTO(Integer deductionId, Double deductionAmt) {
        this.deductionId = deductionId;
        this.deductionAmt = deductionAmt;
    }

    public Integer getDeductionId() {
        return deductionId;
    }

    public void setDeductionId(Integer deductionId) {
        this.deductionId = deductionId;
    }

    public Double getDeductionAmt() {
        return deductionAmt;
    }

    public void setDeductionAmt(Double deductionAmt) {
        this.deductionAmt = deductionAmt;
    }
}
