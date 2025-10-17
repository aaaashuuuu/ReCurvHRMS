package com.hrms.entity;

import java.sql.Time;
import java.time.LocalDate;

import com.hrms.enums.AttendanceStatus;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name="Attendance")
public class Attendance {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attendanceId ;
    
    
    @ManyToOne
    @JoinColumn(name="userId")
    private User user ;
    private LocalDate date ;
    private Time checkIn ;
    private Time lunchIn ;
    private Time lunchOut ;
    private Time checkOut ;



    private double productionHours ;
    @ColumnDefault("9")
    private double workingHours = 9 ;
    private double breakHours ;
    private double lateHours ;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status ;


    public Integer getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Integer attendanceId) {
        this.attendanceId = attendanceId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Time getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Time checkIn) {
        this.checkIn = checkIn;
    }

    public Time getLunchIn() {
        return lunchIn;
    }

    public void setLunchIn(Time lunchIn) {
        this.lunchIn = lunchIn;
    }

    public Time getLunchOut() {
        return lunchOut;
    }

    public void setLunchOut(Time lunchOut) {
        this.lunchOut = lunchOut;
    }

    public Time getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Time checkOut) {
        this.checkOut = checkOut;
    }

    public double getProductionHours() {
        return productionHours;
    }

    public void setProductionHours(double productionHours) {
        this.productionHours = productionHours;
    }

    public double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(double workingHours) {
        this.workingHours = workingHours;
    }

    public double getBreakHours() {
        return breakHours;
    }

    public void setBreakHours(double breakHours) {
        this.breakHours = breakHours;
    }

    public double getLateHours() {
        return lateHours;
    }

    public void setLateHours(double lateHours) {
        this.lateHours = lateHours;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }

    public Attendance(Integer attendanceId, User user, LocalDate date, Time checkIn, Time lunchIn, Time lunchOut, Time checkOut, double productionHours, double workingHours, double breakHours, double lateHours, AttendanceStatus status) {
        this.attendanceId = attendanceId;
        this.user = user;
        this.date = date;
        this.checkIn = checkIn;
        this.lunchIn = lunchIn;
        this.lunchOut = lunchOut;
        this.checkOut = checkOut;
        this.productionHours = productionHours;
        this.workingHours = workingHours;
        this.breakHours = breakHours;
        this.lateHours = lateHours;
        this.status = status;
    }

    public Attendance() {
        super();
    }
}
