package com.trinhnx151.human_resource_management.repository;

import com.trinhnx151.human_resource_management.dto.sdi.department.DepartmentSearchSdi;
import com.trinhnx151.human_resource_management.dto.sdo.department.DepartmentSearchSdo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartmentRepoCustom {
    Page<DepartmentSearchSdo> search(DepartmentSearchSdi request, Pageable pageable);
}
