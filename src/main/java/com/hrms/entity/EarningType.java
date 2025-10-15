package com.hrms.entity;

import jakarta.persistence.*;

@Entity

public class EarningType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer earningTypeId;

    private String earningTypeName;

    public EarningType() {}

    public EarningType(Integer earningTypeId, String earningTypeName) {
        this.earningTypeId = earningTypeId;
        this.earningTypeName = earningTypeName;
    }

    public Integer getEarningTypeId() {
        return earningTypeId;
    }

    public void setEarningTypeId(Integer earningTypeId) {
        this.earningTypeId = earningTypeId;
    }

    public String getEarningTypeName() {
        return earningTypeName;
    }

    public void setEarningTypeName(String earningTypeName) {
        this.earningTypeName = earningTypeName;
    }

    @Override
    public String toString() {
        return "EarningType [earningTypeId=" + earningTypeId + ", earningTypeName=" + earningTypeName + "]";
    }
}
