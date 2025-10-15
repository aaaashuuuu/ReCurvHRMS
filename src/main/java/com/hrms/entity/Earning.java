package com.hrms.entity;

import jakarta.persistence.*;

@Entity
public class Earning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer earningId;

    private Double earningPercentage;

    @ManyToOne
    @JoinColumn(name = "earning_type_id")
    private EarningType earningType;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "designation_id")
    private Designation designation;

    public Earning() {}

    public Earning(Integer earningId, Double earningPercentage, EarningType earningType,
                   Department department, Designation designation) {
        this.earningId = earningId;
        this.earningPercentage = earningPercentage;
        this.earningType = earningType;
        this.department = department;
        this.designation = designation;
    }

    public Integer getEarningId() {
        return earningId;
    }

    public void setEarningId(Integer earningId) {
        this.earningId = earningId;
    }

    public Double getEarningPercentage() {
        return earningPercentage;
    }

    public void setEarningPercentage(Double earningPercentage) {
        this.earningPercentage = earningPercentage;
    }

    public EarningType getEarningType() {
        return earningType;
    }

    public void setEarningType(EarningType earningType) {
        this.earningType = earningType;
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
        return "Earning [earningId=" + earningId + ", earningPercentage=" + earningPercentage +
                ", earningType=" + earningType + ", department=" + department +
                ", designation=" + designation + "]";
    }
}
