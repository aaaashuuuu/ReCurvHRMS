package com.hrms.repository.employee_repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrms.entity.Designation;
@Repository
public interface IDesignationRepository extends JpaRepository<Designation, Integer> {

	
	  List<Designation> findByDepartmentDepartmentId(Integer departmentId);

}
