package com.trinhnx151.human_resource_management.service;

import com.trinhnx151.human_resource_management.dto.sdi.department.DepartmentCreateSdi;
import com.trinhnx151.human_resource_management.dto.sdi.department.DepartmentSearchSdi;
import com.trinhnx151.human_resource_management.dto.sdi.department.DepartmentUpdateSdi;
import com.trinhnx151.human_resource_management.dto.sdo.department.DepartmentCreateSdo;
import com.trinhnx151.human_resource_management.dto.sdo.department.DepartmentSearchSdo;
import com.trinhnx151.human_resource_management.dto.sdo.department.DepartmentSelfSdo;
import com.trinhnx151.human_resource_management.dto.sdo.department.DepartmentUpdateSdo;
import com.trinhnx151.human_resource_management.entity.Department;
import com.trinhnx151.human_resource_management.exception.custom.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DepartmentService {

    List<Department> getAllDepartment();

    Page<DepartmentSearchSdo> search(DepartmentSearchSdi request, Pageable pageable);

    DepartmentSelfSdo findById(Long id) throws NotFoundException;

    DepartmentCreateSdo create(DepartmentCreateSdi request) throws Exception;

    DepartmentUpdateSdo update(DepartmentUpdateSdi request) throws Exception;

    boolean deleteById(Long id);

}
