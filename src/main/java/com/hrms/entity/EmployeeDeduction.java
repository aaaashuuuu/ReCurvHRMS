package com.hrms.entity;

import jakarta.persistence.*;

@Entity
public class EmployeeDeduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeDeductionId;

    private Double deductionAmt;

    @ManyToOne
    @JoinColumn(name = "salary_id")
    private EmpSalary empSalary;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "deduction_id")
    private Deduction deduction;

    public EmployeeDeduction() {}

    public EmployeeDeduction(Integer employeeDeductionId, Double deductionAmt, EmpSalary empSalary, User user, Deduction deduction) {
        this.employeeDeductionId = employeeDeductionId;
        this.deductionAmt = deductionAmt;
        this.empSalary = empSalary;
        this.user = user;
        this.deduction = deduction;
    }

    public Integer getEmployeeDeductionId() { return employeeDeductionId; }
    public void setEmployeeDeductionId(Integer employeeDeductionId) { this.employeeDeductionId = employeeDeductionId; }

    public Double getDeductionAmt() { return deductionAmt; }
    public void setDeductionAmt(Double deductionAmt) { this.deductionAmt = deductionAmt; }

    public EmpSalary getEmpSalary() { return empSalary; }
    public void setEmpSalary(EmpSalary empSalary) { this.empSalary = empSalary; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Deduction getDeduction() { return deduction; }
    public void setDeduction(Deduction deduction) { this.deduction = deduction; }

    @Override
    public String toString() {
        return "EmployeeDeduction [employeeDeductionId=" + employeeDeductionId + ", deductionAmt=" + deductionAmt +
                ", empSalary=" + empSalary + ", user=" + user + ", deduction=" + deduction + "]";
    }
}
