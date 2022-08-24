package com.trinhnx151.human_resource_management.service;

import com.trinhnx151.human_resource_management.dto.sdi.employee.EmployeeCreateSdi;
import com.trinhnx151.human_resource_management.dto.sdi.employee.EmployeeSearchSdi;
import com.trinhnx151.human_resource_management.dto.sdi.employee.EmployeeUpdateSdi;
import com.trinhnx151.human_resource_management.dto.sdo.employee.EmployeeCreateSdo;
import com.trinhnx151.human_resource_management.dto.sdo.employee.EmployeeSearchSdo;
import com.trinhnx151.human_resource_management.dto.sdo.employee.EmployeeSelfSdo;
import com.trinhnx151.human_resource_management.dto.sdo.employee.EmployeeUpdateSdo;
import com.trinhnx151.human_resource_management.entity.Employee;
import com.trinhnx151.human_resource_management.exception.custom.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    Page<EmployeeSearchSdo> search(EmployeeSearchSdi request, Pageable pageable);

    EmployeeSelfSdo findById(Long id) throws NotFoundException;

    EmployeeCreateSdo create(EmployeeCreateSdi request) throws  Exception;

    EmployeeUpdateSdo update(EmployeeUpdateSdi request) throws Exception;

    Boolean deleteById(Long id);

    List<Employee> getAllEmployee();

}
