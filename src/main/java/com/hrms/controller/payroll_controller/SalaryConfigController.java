package com.hrms.controller.payroll_controller;

import com.hrms.dto.payroll_dto.EarningDTO;
import com.hrms.dto.payroll_dto.DeductionDTO;
import com.hrms.entity.*;
import com.hrms.service.employee_service.IDepartmentService;
import com.hrms.service.employee_service.IDesignationService;
import com.hrms.service.payroll_service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/payroll")
public class SalaryConfigController {

    private final IEarningService earningService;
    private final IEarningTypeService earningTypeService;
    private final IDeductionService deductionService;
    private final IDeductionTypeService deductionTypeService;
    private final IDepartmentService departmentService;
    private final IDesignationService designationService;

    public SalaryConfigController(IEarningService earningService,
                                  IEarningTypeService earningTypeService,
                                  IDeductionService deductionService,
                                  IDeductionTypeService deductionTypeService,
                                  IDepartmentService departmentService,
                                  IDesignationService designationService) {
        this.earningService = earningService;
        this.earningTypeService = earningTypeService;
        this.deductionService = deductionService;
        this.deductionTypeService = deductionTypeService;
        this.departmentService = departmentService;
        this.designationService = designationService;
    }

    @GetMapping("/salaryConfig")
    public String loadSalaryConfig(Model model,
                                   @RequestParam(required = false) String type) {
        if (type == null) type = "earning";

        model.addAttribute("currentType", type);
        model.addAttribute("content", "components/payroll/salaryConfig");
        // Note: Data for tables is loaded by AJAX, so no need to add list data here.
        return "base-layout";
    }

    @GetMapping("/list/{type}")
    @ResponseBody
    public Object listData(@PathVariable String type){
        switch(type){
            case "earning":
                List<Earning> earnings = earningService.getAllEarnings();
                return earningService.mapEarningsToDTO(earnings);
            case "deduction":
                List<Deduction> deductions = deductionService.getAllDeductions();
                return deductionService.mapDeductionsToDTO(deductions);
            case "earningType":
                return earningTypeService.getAllEarningTypes();
            case "deductionType":
                return deductionTypeService.getAllDeductionTypes();
            default:
                return ResponseEntity.badRequest().body("Invalid type");
        }
    }

    @PostMapping("/save/{type}")
    @ResponseBody
    public String saveData(@PathVariable String type, @RequestBody Map<String,Object> payload){
        try {
            switch(type){
                case "earning" -> {
                    Integer id = payload.get("id") != null ? Integer.parseInt(payload.get("id").toString()) : null;
                    Integer typeId = Integer.parseInt(payload.get("typeId").toString());
                    Integer deptId = Integer.parseInt(payload.get("departmentId").toString());
                    Integer desigId = Integer.parseInt(payload.get("designationId").toString());
                    Double percentage = Double.parseDouble(payload.get("percentage").toString());

                    EarningType et = earningTypeService.getEarningTypeById(typeId).orElseThrow();
                    Department dept = departmentService.getDepartmentById(deptId);
                    Designation desig = designationService.getDesignationById(desigId);

                    Earning e = new Earning();
                    if(id != null) e.setEarningId(id);
                    e.setEarningType(et);
                    e.setDepartment(dept);
                    e.setDesignation(desig);
                    e.setEarningPercentage(percentage);

                    Optional<Earning> existing = earningService.findByTypeDeptDesig(et, dept, desig);
                    if (existing.isPresent() && (id == null || !existing.get().getEarningId().equals(id))) {
                        // Update existing percentage
                        Earning ex = existing.get();
                        ex.setEarningPercentage(ex.getEarningPercentage() + e.getEarningPercentage());
                        earningService.saveEarning(ex);
                    } else {
                        earningService.saveEarning(e);
                    }
                }
                case "deduction" -> {
                    Integer id = payload.get("id") != null ? Integer.parseInt(payload.get("id").toString()) : null;
                    Integer typeId = Integer.parseInt(payload.get("typeId").toString());
                    Integer deptId = Integer.parseInt(payload.get("departmentId").toString());
                    Integer desigId = Integer.parseInt(payload.get("designationId").toString());
                    Double percentage = Double.parseDouble(payload.get("percentage").toString());

                    DeductionType dt = deductionTypeService.getDeductionTypeById(typeId).orElseThrow();
                    Department dept = departmentService.getDepartmentById(deptId);
                    Designation desig = designationService.getDesignationById(desigId);

                    Deduction d = new Deduction();
                    if(id != null) d.setDeductionId(id);
                    d.setDeductionType(dt);
                    d.setDepartment(dept);
                    d.setDesignation(desig);
                    d.setDeductionPercentage(percentage);

                    Optional<Deduction> existing = deductionService.findByTypeDeptDesig(dt, dept, desig);
                    if (existing.isPresent() && (id == null || !existing.get().getDeductionId().equals(id))) {
                        Deduction ex = existing.get();
                        ex.setDeductionPercentage(ex.getDeductionPercentage() + d.getDeductionPercentage());
                        deductionService.saveDeduction(ex);
                    } else {
                        deductionService.saveDeduction(d);
                    }
                }
                case "earningType" -> {
                    Integer id = payload.get("id") != null ? Integer.parseInt(payload.get("id").toString()) : null;
                    String name = payload.get("name").toString();
                    EarningType et = new EarningType();
                    if(id != null) et.setEarningTypeId(id);
                    et.setEarningTypeName(name);
                    earningTypeService.saveEarningType(et);
                }
                case "deductionType" -> {
                    Integer id = payload.get("id") != null ? Integer.parseInt(payload.get("id").toString()) : null;
                    String name = payload.get("name").toString();
                    DeductionType dt = new DeductionType();
                    if(id != null) dt.setDeductionTypeId(id);
                    dt.setDeductionTypeName(name);
                    deductionTypeService.saveDeductionType(dt);
                }
                default -> throw new IllegalArgumentException("Invalid type");
            }
            return "Saved successfully";
        } catch (Exception e){
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @DeleteMapping("/delete/{type}/{id}")
    @ResponseBody
    public String deleteData(@PathVariable String type, @PathVariable Integer id){
        switch(type){
            case "earning" -> earningService.deleteEarning(id);
            case "deduction" -> deductionService.deleteDeduction(id);
            case "earningType" -> earningTypeService.deleteEarningType(id);
            case "deductionType" -> deductionTypeService.deleteDeductionType(id);
            default -> throw new IllegalArgumentException("Invalid type");
        }
        return "Deleted successfully";
    }

    @GetMapping("/designation/getByDepartment")
    @ResponseBody
    public List<Designation> getDesignationsByDept(@RequestParam Integer deptId) {
        return designationService.getDesignationsByDepartment(deptId);
    }

    @GetMapping("/department/list")
    @ResponseBody
    public List<Department> listDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/list/earningType")
    @ResponseBody
    public List<EarningType> listEarningTypes() {
        return earningTypeService.getAllEarningTypes();
    }

    @GetMapping("/list/deductionType")
    @ResponseBody
    public List<DeductionType> listDeductionTypes() {
        return deductionTypeService.getAllDeductionTypes();
    }
}
