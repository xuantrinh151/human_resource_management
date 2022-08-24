package com.trinhnx151.human_resource_management.controller;

import com.trinhnx151.human_resource_management.dto.sdi.employee.EmployeeCreateSdi;
import com.trinhnx151.human_resource_management.dto.sdi.employee.EmployeeSearchSdi;
import com.trinhnx151.human_resource_management.dto.sdi.employee.EmployeeUpdateSdi;
import com.trinhnx151.human_resource_management.dto.sdo.employee.EmployeeCreateSdo;
import com.trinhnx151.human_resource_management.dto.sdo.employee.EmployeeSearchSdo;
import com.trinhnx151.human_resource_management.dto.sdo.employee.EmployeeSelfSdo;
import com.trinhnx151.human_resource_management.dto.sdo.employee.EmployeeUpdateSdo;
import com.trinhnx151.human_resource_management.entity.Employee;
import com.trinhnx151.human_resource_management.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/search")
    Page<EmployeeSearchSdo> search(EmployeeSearchSdi request, Pageable pageable) {
        return employeeService.search(request, pageable);
    }

    @GetMapping("")
    List<Employee> getAllDepartment() {
        return employeeService.getAllEmployee();
    }

    @GetMapping("/{id}")
    EmployeeSelfSdo findEmployeeById(@PathVariable Long id) {
        return employeeService.findById(id);
    }

    @PostMapping("/{create}")
    @ResponseStatus(HttpStatus.CREATED)
    EmployeeCreateSdo create(@RequestBody @Valid EmployeeCreateSdi request) throws Exception {
        return employeeService.create(request);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    EmployeeUpdateSdo update(@RequestBody @Valid EmployeeUpdateSdi request) throws Exception {
        return employeeService.update(request);
    }

    @DeleteMapping("/{id}/delete")
    Boolean deleteEmployee(@PathVariable Long id) {
        return employeeService.deleteById(id);
    }
}
