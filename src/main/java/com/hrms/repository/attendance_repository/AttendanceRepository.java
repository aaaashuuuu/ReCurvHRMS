package com.hrms.repository.attendance_repository;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hrms.entity.Attendance;
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    // Sum production hours for a user in a given month and year
    @Query("SELECT COALESCE(SUM(a.productionHours), 0) FROM Attendance a " +
           "WHERE a.user.userId = :userId " +
           "AND FUNCTION('MONTH', a.date) = :month " +
           "AND FUNCTION('YEAR', a.date) = :year")
    double sumProductionHoursByUserAndMonthYear(@Param("userId") int userId,
                                                @Param("month") int month, 
                                                @Param("year") int year);
}

