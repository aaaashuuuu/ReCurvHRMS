package com.hrms.entity;

import jakarta.persistence.*;

@Entity
public class Deduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer deductionId;

    private Double deductionPercentage;

    @ManyToOne
    @JoinColumn(name = "deduction_type_id")
    private DeductionType deductionType;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "designation_id")
    private Designation designation;

    public Deduction() {}

    public Deduction(Integer deductionId, Double deductionPercentage, DeductionType deductionType,
                     Department department, Designation designation) {
        this.deductionId = deductionId;
        this.deductionPercentage = deductionPercentage;
        this.deductionType = deductionType;
        this.department = department;
        this.designation = designation;
    }

    public Integer getDeductionId() {
        return deductionId;
    }

    public void setDeductionId(Integer deductionId) {
        this.deductionId = deductionId;
    }

    public Double getDeductionPercentage() {
        return deductionPercentage;
    }

    public void setDeductionPercentage(Double deductionPercentage) {
        this.deductionPercentage = deductionPercentage;
    }

    public DeductionType getDeductionType() {
        return deductionType;
    }

    public void setDeductionType(DeductionType deductionType) {
        this.deductionType = deductionType;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Designation getDesignation() {
        return designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return "Deduction [deductionId=" + deductionId + ", deductionPercentage=" + deductionPercentage +
                ", deductionType=" + deductionType + ", department=" + department +
                ", designation=" + designation + "]";
    }
}
