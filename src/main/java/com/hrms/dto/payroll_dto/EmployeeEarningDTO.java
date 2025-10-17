package com.hrms.dto.payroll_dto;

public class EmployeeEarningDTO {
    private Integer earningId;       // Earning entity ID
    private Double earningAmt;       // Calculated amount

    public EmployeeEarningDTO() {}

    public EmployeeEarningDTO(Integer earningId, Double earningAmt) {
        this.earningId = earningId;
        this.earningAmt = earningAmt;
    }

    public Integer getEarningId() {
        return earningId;
    }

    public void setEarningId(Integer earningId) {
        this.earningId = earningId;
    }

    public Double getEarningAmt() {
        return earningAmt;
    }

    public void setEarningAmt(Double earningAmt) {
        this.earningAmt = earningAmt;
    }
}
