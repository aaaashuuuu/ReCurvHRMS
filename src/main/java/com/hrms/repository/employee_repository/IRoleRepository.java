package com.hrms.repository.employee_repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrms.entity.Role;
@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer> {

}
