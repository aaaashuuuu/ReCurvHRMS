package com.hrms.entity;

import jakarta.persistence.*;

@Entity
public class DeductionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer deductionTypeId;

    private String deductionTypeName;

    public DeductionType() {}

    public DeductionType(Integer deductionTypeId, String deductionTypeName) {
        this.deductionTypeId = deductionTypeId;
        this.deductionTypeName = deductionTypeName;
    }

    public Integer getDeductionTypeId() {
        return deductionTypeId;
    }

    public void setDeductionTypeId(Integer deductionTypeId) {
        this.deductionTypeId = deductionTypeId;
    }

    public String getDeductionTypeName() {
        return deductionTypeName;
    }

    public void setDeductionTypeName(String deductionTypeName) {
        this.deductionTypeName = deductionTypeName;
    }
    
	@Override
    public String toString() {
        return "DeductionType [deductionTypeId=" + deductionTypeId + ", deductionTypeName=" + deductionTypeName + "]";
    }
}
