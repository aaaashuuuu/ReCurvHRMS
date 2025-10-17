package com.hrms.dto.payroll_dto;

public class PayslipDTO {
	
	 private Integer payslipId;
	    private Integer month;
	    private Integer year;
	   
	    private String employeeName;
		
		public PayslipDTO(Integer payslipId, Integer month, Integer year, String employeeName) {
			super();
			this.payslipId = payslipId;
			this.month = month;
			this.year = year;
			
			this.employeeName = employeeName;
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
		
		public String getEmployeeName() {
			return employeeName;
		}
		public void setEmployeeName(String employeeName) {
			this.employeeName = employeeName;
		}
		@Override
		public String toString() {
			return "PayslipDTO [payslipId=" + payslipId + ", month=" + month + ", year=" + year + ", employeeName="
					+ employeeName + "]";
		}
	    
	    

}
