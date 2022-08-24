package com.trinhnx151.human_resource_management.service.impl;

import com.trinhnx151.human_resource_management.dto.sdi.department.DepartmentReportSdi;
import com.trinhnx151.human_resource_management.dto.sdi.employee.EmployeeReportSdi;
import com.trinhnx151.human_resource_management.dto.sdo.department.DepartmentReportSdo;
import com.trinhnx151.human_resource_management.dto.sdo.employee.BestEmployeeReportSdo;
import com.trinhnx151.human_resource_management.dto.sdo.employee.EmployeeReportSdo;
import com.trinhnx151.human_resource_management.repository.ReportRepoCustom;
import com.trinhnx151.human_resource_management.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepoCustom reportRepoCustom;

    @Override
    public Page<DepartmentReportSdo> report(DepartmentReportSdi request, Pageable pageable) {
        return reportRepoCustom.report(request,pageable);
    }

    @Override
    public Page<EmployeeReportSdo> report(EmployeeReportSdi request, Pageable pageable) {
        return reportRepoCustom.report(request,pageable);
    }

    @Override
    public Page<BestEmployeeReportSdo> reportBestEmployee(Pageable pageable) {
        return reportRepoCustom.reportBestEmployee(pageable);
    }

}
