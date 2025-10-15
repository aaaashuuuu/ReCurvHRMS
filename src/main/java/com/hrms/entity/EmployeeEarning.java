package com.hrms.entity;

import jakarta.persistence.*;

@Entity
public class EmployeeEarning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeEarningId;

    private Double earningAmt;

    @ManyToOne
    @JoinColumn(name = "salary_id")
    private EmpSalary empSalary;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "earning_id")
    private Earning earning;

    public EmployeeEarning() {}

    public EmployeeEarning(Integer employeeEarningId, Double earningAmt, EmpSalary empSalary, User user, Earning earning) {
        this.employeeEarningId = employeeEarningId;
        this.earningAmt = earningAmt;
        this.empSalary = empSalary;
        this.user = user;
        this.earning = earning;
    }

    public Integer getEmployeeEarningId() { return employeeEarningId; }
    public void setEmployeeEarningId(Integer employeeEarningId) { this.employeeEarningId = employeeEarningId; }

    public Double getEarningAmt() { return earningAmt; }
    public void setEarningAmt(Double earningAmt) { this.earningAmt = earningAmt; }

    public EmpSalary getEmpSalary() { return empSalary; }
    public void setEmpSalary(EmpSalary empSalary) { this.empSalary = empSalary; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Earning getEarning() { return earning; }
    public void setEarning(Earning earning) { this.earning = earning; }

    @Override
    public String toString() {
        return "EmployeeEarning [employeeEarningId=" + employeeEarningId + ", earningAmt=" + earningAmt +
                ", empSalary=" + empSalary + ", user=" + user + ", earning=" + earning + "]";
    }
}
