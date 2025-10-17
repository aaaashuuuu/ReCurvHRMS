package com.hrms.dto.payroll_dto;

public class PayslipRequestDTO {

    private Integer employeeId;
    private Integer month;
    private Integer year;

    public Integer getEmployeeId() { return employeeId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }
    public Integer getMonth() { return month; }
    public void setMonth(Integer month) { this.month = month; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
}

