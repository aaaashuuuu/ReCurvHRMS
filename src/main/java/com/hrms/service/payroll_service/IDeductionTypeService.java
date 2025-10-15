package com.hrms.service.payroll_service;

import com.hrms.entity.DeductionType;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public interface IDeductionTypeService {
    List<DeductionType> getAllDeductionTypes();
    Optional<DeductionType> getDeductionTypeById(Integer id);
    DeductionType saveDeductionType(DeductionType deductionType);
    DeductionType updateDeductionType(DeductionType deductionType);
    void deleteDeductionType(Integer id);
}

