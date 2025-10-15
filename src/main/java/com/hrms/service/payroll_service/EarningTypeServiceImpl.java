package com.hrms.service.payroll_service;

import com.hrms.entity.EarningType;
import com.hrms.repository.payroll_repository.*;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EarningTypeServiceImpl implements IEarningTypeService {

    private final EarningTypeRepository earningTypeRepository;

    public EarningTypeServiceImpl(EarningTypeRepository earningTypeRepository) {
        this.earningTypeRepository = earningTypeRepository;
    }

    @Override
    public List<EarningType> getAllEarningTypes() {
        return earningTypeRepository.findAll();
    }

    @Override
    public Optional<EarningType> getEarningTypeById(Integer id) {
        return earningTypeRepository.findById(id);
    }

    @Override
    public EarningType saveEarningType(EarningType earningType) {
        return earningTypeRepository.save(earningType);
    }

    @Override
    public EarningType updateEarningType(EarningType earningType) {
        return earningTypeRepository.save(earningType);
    }

    @Override
    public void deleteEarningType(Integer id) {
        earningTypeRepository.deleteById(id);
    }
}

