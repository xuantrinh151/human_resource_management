package com.trinhnx151.human_resource_management.controller;

import com.trinhnx151.human_resource_management.dto.sdi.department.DepartmentReportSdi;
import com.trinhnx151.human_resource_management.dto.sdi.employee.EmployeeReportSdi;
import com.trinhnx151.human_resource_management.dto.sdo.department.DepartmentReportSdo;
import com.trinhnx151.human_resource_management.dto.sdo.employee.BestEmployeeReportSdo;
import com.trinhnx151.human_resource_management.dto.sdo.employee.EmployeeReportSdo;
import com.trinhnx151.human_resource_management.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/department")
    Page<DepartmentReportSdo> reportDepartment(DepartmentReportSdi request, Pageable pageable) {
        return reportService.report(request, pageable);
    }

    @GetMapping("/employee")
    Page<EmployeeReportSdo> reportEmployee(EmployeeReportSdi request,Pageable pageable) {
        return reportService.report(request, pageable);
    }

    @GetMapping("/employee/10")
    Page<BestEmployeeReportSdo> reportBestEmployee(Pageable pageable) {
        return reportService.reportBestEmployee(pageable);
    }
}
