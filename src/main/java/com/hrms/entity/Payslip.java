package com.hrms.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity

public class Payslip {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Integer payslipId;

	private Integer month;
	private Integer year;

	private LocalDateTime generatedAt;

	private String payslipPath;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Payslip() {
	}

	public Payslip(Integer payslipId, Integer month, Integer year, LocalDateTime generatedAt, String payslipPath,
			User user) {
		this.payslipId = payslipId;
		this.month = month;
		this.year = year;
		this.generatedAt = generatedAt;
		this.payslipPath = payslipPath;
		this.user = user;
	}

	public Integer getPayslipId() {
		return payslipId;
	}

	public void setPayslipId(Integer payslipId) {
		this.payslipId = payslipId;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public LocalDateTime getGeneratedAt() {
		return generatedAt;
	}

	public void setGeneratedAt(LocalDateTime generatedAt) {
		this.generatedAt = generatedAt;
	}

	public String getPayslipPath() {
		return payslipPath;
	}

	public void setPayslipPath(String payslipPath) {
		this.payslipPath = payslipPath;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Payslip [payslipId=" + payslipId + ", month=" + month + ", year=" + year + ", generatedAt="
				+ generatedAt + ", payslipPath=" + payslipPath + ", user=" + user + "]";
	}
}
