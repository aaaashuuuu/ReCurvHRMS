package com.hrms.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class EmpSalary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer salaryId;

	private Double totalSalary;

	private Double netSalary;

	private LocalDateTime createdDate;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public EmpSalary() {
	}

	public EmpSalary(Integer salaryId, Double totalSalary, Double netSalary, LocalDateTime createdDate, User user) {
		this.salaryId = salaryId;
		this.totalSalary = totalSalary;
		this.netSalary = netSalary;
		this.createdDate = createdDate;
		this.user = user;
	}

	public Integer getSalaryId() {
		return salaryId;
	}

	public void setSalaryId(Integer salaryId) {
		this.salaryId = salaryId;
	}

	public Double getTotalSalary() {
		return totalSalary;
	}

	public void setTotalSalary(Double totalSalary) {
		this.totalSalary = totalSalary;
	}

	public Double getNetSalary() {
		return netSalary;
	}

	public void setNetSalary(Double netSalary) {
		this.netSalary = netSalary;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "EmpSalary [salaryId=" + salaryId + ", totalSalary=" + totalSalary + ", netSalary=" + netSalary
				+ ", createdDate=" + createdDate + ", user=" + user + "]";
	}
}
