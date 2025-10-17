package com.hrms.dto.payroll_dto;

import java.util.List;
import java.util.stream.Collectors;

import com.hrms.entity.Deduction;
import com.hrms.entity.Earning;

public class PayrollMapper {

    public static List<DeductionDTO> mapDeductionsToDTO(List<Deduction> deductions) {
        return deductions.stream()
            .map(d -> new DeductionDTO(
                d.getDeductionId(),
                d.getDeductionType().getDeductionTypeId(),
                d.getDeductionType().getDeductionTypeName(),
                d.getDeductionPercentage(),
                d.getDepartment().getDepartmentId(),
                d.getDepartment().getDepartmentName(),
                d.getDesignation().getDesignationId(),
                d.getDesignation().getDesignationName()
            ))
            .collect(Collectors.toList());
    }

    public static List<EarningDTO> mapEarningsToDTO(List<Earning> earnings) {
        return earnings.stream()
            .map(e -> new EarningDTO(
                e.getEarningId(),
                e.getEarningType().getEarningTypeId(),
                e.getEarningType().getEarningTypeName(),
                e.getEarningPercentage(),
                e.getDepartment().getDepartmentId(),
                e.getDepartment().getDepartmentName(),
                e.getDesignation().getDesignationId(),
                e.getDesignation().getDesignationName()
            ))
            .collect(Collectors.toList());
    }
}
