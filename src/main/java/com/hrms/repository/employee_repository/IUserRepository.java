package com.hrms.repository.employee_repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrms.entity.User;
@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

}
