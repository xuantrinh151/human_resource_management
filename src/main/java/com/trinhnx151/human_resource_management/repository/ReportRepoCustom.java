package com.trinhnx151.human_resource_management.repository;

import com.trinhnx151.human_resource_management.dto.sdi.department.DepartmentReportSdi;
import com.trinhnx151.human_resource_management.dto.sdi.employee.EmployeeReportSdi;
import com.trinhnx151.human_resource_management.dto.sdo.department.DepartmentReportSdo;
import com.trinhnx151.human_resource_management.dto.sdo.employee.BestEmployeeReportSdo;
import com.trinhnx151.human_resource_management.dto.sdo.employee.EmployeeReportSdo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepoCustom {
    Page<DepartmentReportSdo> report(DepartmentReportSdi request, Pageable pageable);

    Page<EmployeeReportSdo> report(EmployeeReportSdi request, Pageable pageable);

    Page<BestEmployeeReportSdo> reportBestEmployee(Pageable pageable);
}
