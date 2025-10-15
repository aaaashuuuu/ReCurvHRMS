package com.hrms.service.payroll_service;


import com.hrms.entity.DeductionType;
import com.hrms.repository.payroll_repository.*;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeductionTypeServiceImpl implements IDeductionTypeService {

    private final DeductionTypeRepository deductionTypeRepository;

    public DeductionTypeServiceImpl(DeductionTypeRepository deductionTypeRepository) {
        this.deductionTypeRepository = deductionTypeRepository;
    }

    @Override
    public List<DeductionType> getAllDeductionTypes() {
        return deductionTypeRepository.findAll();
    }

    @Override
    public Optional<DeductionType> getDeductionTypeById(Integer id) {
        return deductionTypeRepository.findById(id);
    }

    @Override
    public DeductionType saveDeductionType(DeductionType deductionType) {
        return deductionTypeRepository.save(deductionType);
    }

    @Override
    public DeductionType updateDeductionType(DeductionType deductionType) {
        return deductionTypeRepository.save(deductionType);
    }

    @Override
    public void deleteDeductionType(Integer id) {
        deductionTypeRepository.deleteById(id);
    }
}

