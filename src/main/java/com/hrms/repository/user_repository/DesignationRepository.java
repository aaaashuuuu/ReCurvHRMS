package com.hrms.repository.user_repository;

import com.hrms.entity.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Integer> {
    Optional<Designation> findByDesignationName(String designationName);

    @Query("SELECT d FROM Designation d JOIN FETCH d.department WHERE d.designationId = :id")
    Optional<Designation> findByIdWithDepartment(@Param("id") Integer id);

    List<Designation> findByStatus(com.hrms.entity.Status status);
}