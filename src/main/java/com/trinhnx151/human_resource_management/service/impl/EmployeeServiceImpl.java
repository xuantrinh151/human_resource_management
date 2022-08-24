package com.trinhnx151.human_resource_management.service.impl;

import com.trinhnx151.human_resource_management.dto.sdi.employee.EmployeeCreateSdi;
import com.trinhnx151.human_resource_management.dto.sdi.employee.EmployeeSearchSdi;
import com.trinhnx151.human_resource_management.dto.sdi.employee.EmployeeUpdateSdi;
import com.trinhnx151.human_resource_management.dto.sdo.employee.EmployeeCreateSdo;
import com.trinhnx151.human_resource_management.dto.sdo.employee.EmployeeSearchSdo;
import com.trinhnx151.human_resource_management.dto.sdo.employee.EmployeeSelfSdo;
import com.trinhnx151.human_resource_management.dto.sdo.employee.EmployeeUpdateSdo;
import com.trinhnx151.human_resource_management.entity.Department;
import com.trinhnx151.human_resource_management.entity.Employee;
import com.trinhnx151.human_resource_management.exception.custom.DuplicateException;
import com.trinhnx151.human_resource_management.exception.custom.NotFoundException;
import com.trinhnx151.human_resource_management.repository.DepartmentRepo;
import com.trinhnx151.human_resource_management.repository.EmployeeRepo;
import com.trinhnx151.human_resource_management.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepo employeeRepo;
    private final DepartmentRepo departmentRepo;
    @Override
    public Page<EmployeeSearchSdo> search(EmployeeSearchSdi request, Pageable pageable) {
        return employeeRepo.search(request,pageable);
    }

    @Override
    public EmployeeSelfSdo findById(Long id) throws NotFoundException {
        Optional<Employee> employee = employeeRepo.findById(id);
        if (employee.isPresent()){
            return EmployeeSelfSdo.builder()
                    .id(employee.get().getId())
                    .code(employee.get().getCode())
                    .fullName(employee.get().getName())
                    .gender(employee.get().isGender())
                    .image(employee.get().getImage())
                    .dob(employee.get().getDob())
                    .salary(employee.get().getSalary())
                    .level(employee.get().getLevel())
                    .email(employee.get().getEmail())
                    .phone(employee.get().getPhone())
                    .status(employee.get().getStatus())
                    .departmentId(employee.get().getDepartmentId())
                    .build();
        }else {
            throw new NotFoundException("Không tìm thấy nhân viên với id [" + id + "]");
        }
    }

    @Override
    public EmployeeCreateSdo create(EmployeeCreateSdi request) {
        this.validateCreate(request);
        Employee employee = request.toEmployee();
        employeeRepo.save(employee);
        return EmployeeCreateSdo.builder()
                .id(employee.getId())
                .build();
    }

    private void validateCreate(EmployeeCreateSdi request) {
        String code = request.getCode().toUpperCase();
        String email = request.getEmail();
        String phone = request.getPhone();
        Long departmentId = request.getDepartmentId();

        Optional<Department> foundDepartment = departmentRepo.findById(departmentId);
        if (foundDepartment.isEmpty()) {
            throw new NotFoundException("Không tìm thấy phòng ban với: [" + departmentId + "]");
        }
        Optional<Employee> employeeByCode = employeeRepo.findByCode(code);
        if (employeeByCode.isPresent()) {
            throw new DuplicateException(("Mã nhân viên [" + code + "] đã tồn tại"));
        }
        Optional<Employee> employeeByPhone = employeeRepo.findByPhone(phone);
        if (employeeByPhone.isPresent()) {
            throw new DuplicateException(("Số điện thoại [" + phone + "] đã tồn tại"));
        }
        Optional<Employee> employeeByEmail= employeeRepo.findByEmail(email);
        if (employeeByEmail.isPresent()) {
            throw new DuplicateException(("Email [" + email + "] đã tồn tại"));
        }
    }

    @Override
    public EmployeeUpdateSdo update(EmployeeUpdateSdi request) {
        this.validateUpdate(request);
        Employee employee = request.toEmployee();
        employeeRepo.save(employee);
        return EmployeeUpdateSdo.builder()
                    .id(employee.getId())
                    .build();
    }

    private void validateUpdate(EmployeeUpdateSdi request) {
        Long id = request.getId();
        String code = request.getCode();
        String email = request.getEmail();
        String phone = request.getPhone();
        Long departmentId = request.getDepartmentId();
        Optional<Department> foundDepartment = departmentRepo.findById(departmentId);
        if (foundDepartment.isEmpty()) {
            throw new NotFoundException("Không tìm thấy phòng ban với: [" + departmentId + "]");
        }
        Optional<Employee> foundEmployee = employeeRepo.findByIdWithAllStatus(id);
        if (foundEmployee.isEmpty()) {
            throw new NotFoundException("Không tìm thấy nhân viên với id: [" + id + "]");
        }
        Optional<Employee> employeeByCode = employeeRepo.findByCode(code,id);
        if (employeeByCode.isPresent()) {
            throw new DuplicateException("Mã nhân viên [" + code + "] đã tồn tại");
        }
        Optional<Employee> employeeByEmail = employeeRepo.findByEmail(email,id);
        if (employeeByEmail.isPresent()) {
            throw new DuplicateException("Email [" + email + "] đã tồn tại");
        }
        Optional<Employee> employeeByPhone = employeeRepo.findByPhone(phone,id);
        if (employeeByPhone.isPresent()) {
            throw new DuplicateException("Số điện thoại [" + phone + "] đã tồn tại");
        }
    }

    @Override
    public Boolean deleteById(Long id) {
        Optional<Employee> foundEmployee = employeeRepo.findById(id);
        if (foundEmployee.isPresent()) {
            foundEmployee.get().setStatus(2);
            employeeRepo.save(foundEmployee.get());
            return true;
        }
        return false;
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepo.findAll();
    }
}
