package com.hrms.dto.payroll_dto;

public class DeductionDTO {
    private Integer id;
    private Integer typeId;
    private String typeName;
    private Double percentage;
    private Integer departmentId;
    private String departmentName;
    private Integer designationId;
    private String designationName;
	public DeductionDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DeductionDTO(Integer id, Integer typeId, String typeName, Double percentage, Integer departmentId,
			String departmentName, Integer designationId, String designationName) {
		super();
		this.id = id;
		this.typeId = typeId;
		this.typeName = typeName;
		this.percentage = percentage;
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.designationId = designationId;
		this.designationName = designationName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Double getPercentage() {
		return percentage;
	}
	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public Integer getDesignationId() {
		return designationId;
	}
	public void setDesignationId(Integer designationId) {
		this.designationId = designationId;
	}
	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

   
}