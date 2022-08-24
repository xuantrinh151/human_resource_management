package com.trinhnx151.human_resource_management.repository;

import com.trinhnx151.human_resource_management.dto.sdi.employee.EmployeeSearchSdi;
import com.trinhnx151.human_resource_management.dto.sdo.employee.EmployeeSearchSdo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeRepoCustom {
    Page<EmployeeSearchSdo> search(EmployeeSearchSdi request, Pageable pageable);


}
