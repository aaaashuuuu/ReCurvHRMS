package com.hrms.service.payroll_service;

import com.hrms.entity.EarningType;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public interface IEarningTypeService {
	List<EarningType> getAllEarningTypes();

	Optional<EarningType> getEarningTypeById(Integer id);

	EarningType saveEarningType(EarningType earningType);

	EarningType updateEarningType(EarningType earningType);

	void deleteEarningType(Integer id);

}
