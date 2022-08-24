package com.trinhnx151.human_resource_management.controller;

import com.trinhnx151.human_resource_management.dto.sdi.department.DepartmentCreateSdi;
import com.trinhnx151.human_resource_management.dto.sdi.department.DepartmentSearchSdi;
import com.trinhnx151.human_resource_management.dto.sdi.department.DepartmentUpdateSdi;
import com.trinhnx151.human_resource_management.dto.sdo.department.DepartmentCreateSdo;
import com.trinhnx151.human_resource_management.dto.sdo.department.DepartmentSearchSdo;
import com.trinhnx151.human_resource_management.dto.sdo.department.DepartmentSelfSdo;
import com.trinhnx151.human_resource_management.dto.sdo.department.DepartmentUpdateSdo;
import com.trinhnx151.human_resource_management.entity.Department;
import com.trinhnx151.human_resource_management.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("/search")
    Page<DepartmentSearchSdo> getDepartmentByCodeOrName(DepartmentSearchSdi request, Pageable pageable) {
        return departmentService.search(request, pageable);
    }

    @GetMapping("")
    List<Department> getAllDepartment() {
        return departmentService.getAllDepartment();
    }

    @GetMapping("/{id}")
    DepartmentSelfSdo findDepartmentById(@PathVariable Long id) {
        return departmentService.findById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    DepartmentCreateSdo create(@RequestBody @Valid DepartmentCreateSdi request) throws Exception {
        return departmentService.create(request);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    DepartmentUpdateSdo update(@RequestBody @Valid DepartmentUpdateSdi request) throws Exception {
        return departmentService.update(request);
    }

    @DeleteMapping("/{id}/delete")
    Boolean deleteDepartment(@PathVariable Long id) {
        return departmentService.deleteById(id);
    }
}
