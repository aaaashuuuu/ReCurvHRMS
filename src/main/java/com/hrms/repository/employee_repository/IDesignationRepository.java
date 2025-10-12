package com.hrms.repository.employee_repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrms.entity.Designation;
@Repository
public interface IDesignationRepository extends JpaRepository<Designation, Integer> {

}
