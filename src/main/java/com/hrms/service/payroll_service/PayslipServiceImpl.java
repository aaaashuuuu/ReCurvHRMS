package com.hrms.service.payroll_service;

import com.hrms.dto.payroll_dto.DeductionDTO;
import com.hrms.dto.payroll_dto.EarningDTO;
import com.hrms.dto.payroll_dto.PayrollMapper;
import com.hrms.entity.*;
import com.hrms.repository.attendance_repository.AttendanceRepository;
import com.hrms.repository.payroll_repository.*;
import com.hrms.repository.user_repository.UserRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class PayslipServiceImpl implements IPayslipService {


    private final UserRepository userRepository;

    private final EmployeeSalaryRepository employeeSalaryRepository;
    private final EmployeeDeductionRepository employeeDeductionRepository;
    private final EmployeeEarningRepository employeeEarningRepository;
    private final PayslipRepository payslipRepository;
    private final DeductionRepository deductionRepository;
    private final EarningRepository earningRepository;
    private final AttendanceRepository attendanceRepository;

   

    public PayslipServiceImpl(EmployeeSalaryRepository employeeSalaryRepository,
			EmployeeDeductionRepository employeeDeductionRepository,
			EmployeeEarningRepository employeeEarningRepository, PayslipRepository payslipRepository,
			DeductionRepository deductionRepository, EarningRepository earningRepository,
			AttendanceRepository attendanceRepository, UserRepository userRepository) {
		super();
		this.employeeSalaryRepository = employeeSalaryRepository;
		this.employeeDeductionRepository = employeeDeductionRepository;
		this.employeeEarningRepository = employeeEarningRepository;
		this.payslipRepository = payslipRepository;
		this.deductionRepository = deductionRepository;
		this.earningRepository = earningRepository;
		this.attendanceRepository = attendanceRepository;
		this.userRepository = userRepository;
		
	}



    public List<EmpSalary> getAllSalaries() {
        return employeeSalaryRepository.findAll();
    }

    public Optional<EmpSalary> getSalaryById(Integer id) {
        return employeeSalaryRepository.findById(id);
    }

    public Page<EmpSalary> getSalariesByUserId(Integer userId, Pageable pageable) {
        return employeeSalaryRepository.findByUser_UserId(userId, pageable);
    }

    public EmpSalary saveSalary(EmpSalary salary) {
        return employeeSalaryRepository.save(salary);
    }

    public void deleteSalary(Integer id) {
        employeeSalaryRepository.deleteById(id);
    }



    public List<Payslip> getPayslipsByUser(int userId, Pageable pageable) {
        return payslipRepository.findByUser_UserId(userId, pageable).getContent();
    }

    public Payslip getPayslipByUserAndMonthYear(int userId, int month, int year) {
        return payslipRepository.findByUser_UserIdAndMonthAndYear(userId, month, year);
    }

    public void savePayslip(Payslip payslip) {
        payslipRepository.save(payslip);
    }



    public List<Deduction> getAllDeductions() {
        return deductionRepository.findAll();
    }

    public Optional<Deduction> getDeductionById(Integer id) {
        return deductionRepository.findById(id);
    }

    public List<Deduction> getDeductionsByDepartmentAndDesignation(Integer deptId, Integer desigId) {
        return deductionRepository.findByDepartmentDepartmentIdAndDesignationDesignationId(deptId, desigId);
    }

    public List<DeductionDTO> mapDeductionsToDTO(List<Deduction> deductions) {
        return PayrollMapper.mapDeductionsToDTO(deductions);
    }


    public List<Earning> getAllEarnings() {
        return earningRepository.findAll();
    }

    public Optional<Earning> getEarningById(Integer id) {
        return earningRepository.findById(id);
    }

    public List<Earning> getEarningsByDepartmentAndDesignation(Integer deptId, Integer desigId) {
        return earningRepository.findByDepartmentDepartmentIdAndDesignationDesignationId(deptId, desigId);
    }

    public List<EarningDTO> mapEarningsToDTO(List<Earning> earnings) {
        return PayrollMapper.mapEarningsToDTO(earnings);
    }

   

    @Override
    @Transactional
    public String generateMonthlyPayslip(int userId, int month, int year) throws Exception {
       
        EmpSalary salary = employeeSalaryRepository.findTopByUser_UserIdOrderByCreatedDateDesc(userId).get();

        List<EmployeeEarning> earnings = employeeEarningRepository.findByUser_UserIdAndEmpSalary_SalaryId(userId, salary.getSalaryId());
        List<EmployeeDeduction> deductions = employeeDeductionRepository.findByUser_UserIdAndEmpSalary_SalaryId(userId, salary.getSalaryId());

        double totalEarnings = earnings.stream().mapToDouble(EmployeeEarning::getEarningAmt).sum();
        double totalDeductions = deductions.stream().mapToDouble(EmployeeDeduction::getDeductionAmt).sum();

        double baseSalary = salary.getTotalSalary() + totalEarnings - totalDeductions;

        YearMonth currentMonth = YearMonth.of(year, month);
        int standHours = currentMonth.lengthOfMonth();
        double standardHours = standHours * 9; 
        
        double hourlyRate = salary.getTotalSalary() / standardHours;


        double totalHoursWorked = attendanceRepository.sumProductionHoursByUserAndMonthYear(userId, month, year);


        double calculatedSalary = (baseSalary / standardHours) * totalHoursWorked;


        String folderPath = "C:/Payslips";
        File folder = new File(folderPath);
        if (!folder.exists()) folder.mkdirs();

        String fileName = folderPath + File.separator + salary.getUser().getFirstName() + "_" + salary.getUser().getLastName() 
            + "_Payslip_" + month + "_" + year + ".pdf";


        Document document = new Document(PageSize.A4, 36, 36, 50, 50);
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE);
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.BLACK);

        Paragraph title = new Paragraph("Payslip", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" "));

       
        PdfPTable empTable = new PdfPTable(2);
        empTable.setWidthPercentage(100);
        empTable.setSpacingBefore(10f);
        empTable.setSpacingAfter(10f);
        empTable.setWidths(new int[]{1, 2});

        empTable.addCell(createCell("Employee Name:", headerFont, BaseColor.GRAY));
        empTable.addCell(createCell(salary.getUser().getFirstName() + "_" + salary.getUser().getLastName(), normalFont, BaseColor.LIGHT_GRAY));

        empTable.addCell(createCell("Month / Year:", headerFont, BaseColor.GRAY));
        empTable.addCell(createCell(month + " / " + year, normalFont, BaseColor.LIGHT_GRAY));

        empTable.addCell(createCell("Base Salary:", headerFont, BaseColor.GRAY));
        empTable.addCell(createCell(String.valueOf(salary.getTotalSalary()), normalFont, BaseColor.LIGHT_GRAY));

        empTable.addCell(createCell("Net Salary:", headerFont, BaseColor.GRAY));
        empTable.addCell(createCell(String.valueOf(calculatedSalary), normalFont, BaseColor.LIGHT_GRAY));

        empTable.addCell(createCell("Total Production Hours:", headerFont, BaseColor.GRAY));
        empTable.addCell(createCell(String.format("%.2f", totalHoursWorked), normalFont, BaseColor.LIGHT_GRAY));

        empTable.addCell(createCell("Total Working Hours:", headerFont, BaseColor.GRAY));
        empTable.addCell(createCell(String.valueOf(standardHours), normalFont, BaseColor.LIGHT_GRAY));

        empTable.addCell(createCell("Hourly Rate:", headerFont, BaseColor.GRAY));
        empTable.addCell(createCell(String.format("%.2f", hourlyRate) + "$", normalFont, BaseColor.LIGHT_GRAY));

        document.add(empTable);


        Paragraph earningTitle = new Paragraph("Earnings", headerFont);
        earningTitle.setAlignment(Element.ALIGN_LEFT);
        document.add(earningTitle);

        PdfPTable earningTable = new PdfPTable(3);
        earningTable.setWidthPercentage(100);
        earningTable.setSpacingBefore(5f);
        earningTable.setWidths(new int[]{3, 1, 1}); // Type, Percentage, Amount

        earningTable.addCell(createCell("Earning Type", headerFont, BaseColor.DARK_GRAY));
        earningTable.addCell(createCell("Percentage", headerFont, BaseColor.DARK_GRAY));
        earningTable.addCell(createCell("Amount", headerFont, BaseColor.DARK_GRAY));

        for (EmployeeEarning e : earnings) {
            earningTable.addCell(createCell(e.getEarning().getEarningType().getEarningTypeName(), normalFont, BaseColor.WHITE));
            earningTable.addCell(createCell(String.format("%.2f%%", e.getEarning().getEarningPercentage()), normalFont, BaseColor.WHITE));
            earningTable.addCell(createCell(String.valueOf(e.getEarningAmt()), normalFont, BaseColor.WHITE));
        }

        earningTable.addCell(createCell("Total Earnings", headerFont, BaseColor.GRAY));
        earningTable.addCell(createCell("", headerFont, BaseColor.GRAY));
        earningTable.addCell(createCell(String.valueOf(totalEarnings), headerFont, BaseColor.GRAY));

        document.add(earningTable);


        Paragraph deductionTitle = new Paragraph("Deductions", headerFont);
        deductionTitle.setAlignment(Element.ALIGN_LEFT);
        deductionTitle.setSpacingBefore(10f);
        document.add(deductionTitle);

        PdfPTable deductionTable = new PdfPTable(3);
        deductionTable.setWidthPercentage(100);
        deductionTable.setSpacingBefore(5f);
        deductionTable.setWidths(new int[]{3, 1, 1}); // Type, Percentage, Amount

        deductionTable.addCell(createCell("Deduction Type", headerFont, BaseColor.DARK_GRAY));
        deductionTable.addCell(createCell("Percentage", headerFont, BaseColor.DARK_GRAY));
        deductionTable.addCell(createCell("Amount", headerFont, BaseColor.DARK_GRAY));

        for (EmployeeDeduction d : deductions) {
            deductionTable.addCell(createCell(d.getDeduction().getDeductionType().getDeductionTypeName(), normalFont, BaseColor.WHITE));
            deductionTable.addCell(createCell(String.format("%.2f%%", d.getDeduction().getDeductionPercentage()), normalFont, BaseColor.WHITE));
            deductionTable.addCell(createCell(String.valueOf(d.getDeductionAmt()), normalFont, BaseColor.WHITE));
        }

        deductionTable.addCell(createCell("Total Deductions", headerFont, BaseColor.GRAY));
        deductionTable.addCell(createCell("", headerFont, BaseColor.GRAY));
        deductionTable.addCell(createCell(String.valueOf(totalDeductions), headerFont, BaseColor.GRAY));

        document.add(deductionTable);

        Paragraph netSalaryPara = new Paragraph("Net Salary: " + calculatedSalary, titleFont);
        netSalaryPara.setAlignment(Element.ALIGN_RIGHT);
        netSalaryPara.setSpacingBefore(15f);
        document.add(netSalaryPara);

        document.close();

        User user = userRepository.findById(userId).get();
     


        Payslip payslip = new Payslip();
        payslip.setUser(user);
        payslip.setMonth(month);
        payslip.setYear(year);
        payslip.setGeneratedAt(LocalDateTime.now());
        payslip.setPayslipPath(fileName);

        
        try {
            payslipRepository.save(payslip);
            System.out.println("Salary Saved");
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        

        return fileName;
        }

        private PdfPCell createCell(String text, Font font, BaseColor bgColor) {
            PdfPCell cell = new PdfPCell(new Phrase(text, font));
            cell.setBackgroundColor(bgColor);
            cell.setPadding(5);
            return cell;
        }

        @Override
        public List<Payslip> getAllPayslips() {
            return payslipRepository.findAll();
        }

        @Override
        public List<Payslip> getAllPayslipsByUserId(Integer userId) {
            return payslipRepository.findByUser_UserId(userId);
        }

		@Override
		public Optional<Payslip> getPayslipById(Integer payslipId) {
			// TODO Auto-generated method stub
			return payslipRepository.findById(payslipId);
		}
		
		public EmpSalary getLatestSalaryByUserId(int userId) throws Exception {
		    return employeeSalaryRepository.findTopByUser_UserIdOrderByCreatedDateDesc(userId)
		            .orElseThrow(() -> new Exception("No salary found for user: " + userId));
		}



}
