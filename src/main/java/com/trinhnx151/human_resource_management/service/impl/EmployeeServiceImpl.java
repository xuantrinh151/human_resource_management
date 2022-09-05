package com.trinhnx151.human_resource_management.service.impl;

import com.trinhnx151.human_resource_management.dto.sdi.employee.EmployeeCreateSdi;
import com.trinhnx151.human_resource_management.dto.sdi.employee.EmployeeLoginSdi;
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
import com.trinhnx151.human_resource_management.service.StorageService;
import com.trinhnx151.human_resource_management.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepo employeeRepo;
    private final DepartmentRepo departmentRepo;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final StorageService storageService;

    @Value("${project.image}")
    private String path;

    @Override
    public Page<EmployeeSearchSdo> search(EmployeeSearchSdi request, Pageable pageable) {
        return employeeRepo.search(request, pageable);
    }

    @Override
    public EmployeeSelfSdo findById(Long id) throws NotFoundException {
        Optional<Employee> employee = employeeRepo.findById(id);
        if (employee.isPresent()) {
            return EmployeeSelfSdo.builder()
                    .id(employee.get().getId())
                    .code(employee.get().getCode())
                    .fullName(employee.get().getName())
                    .gender(employee.get().isGender())
                    .image( employee.get().getImage())
                    .dob(employee.get().getDob())
                    .salary(employee.get().getSalary())
                    .level(employee.get().getLevel())
                    .email(employee.get().getEmail())
                    .phone(employee.get().getPhone())
                    .status(employee.get().getStatus())
                    .departmentId(employee.get().getDepartmentId())
                    .build();
        } else {
            throw new NotFoundException("Không tìm thấy nhân viên với id [" + id + "]");
        }
    }

    @Override
    public EmployeeCreateSdo create(EmployeeCreateSdi request) throws IOException {
        this.validateCreate(request);
        Employee employee = request.toEmployee();
        employee.setImage(storageService.upload(request.getImage()));
        //employee.setPassword(passwordEncoder.encode(employee.getPassword()));
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
        Optional<Employee> employeeByEmail = employeeRepo.findByEmail(email);
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
        Optional<Employee> employeeByCode = employeeRepo.findByCode(code, id);
        if (employeeByCode.isPresent()) {
            throw new DuplicateException("Mã nhân viên [" + code + "] đã tồn tại");
        }
        Optional<Employee> employeeByEmail = employeeRepo.findByEmail(email, id);
        if (employeeByEmail.isPresent()) {
            throw new DuplicateException("Email [" + email + "] đã tồn tại");
        }
        Optional<Employee> employeeByPhone = employeeRepo.findByPhone(phone, id);
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

    @Override
    public Map<String, EmployeeSelfSdo> login(Map<String, EmployeeLoginSdi> request,HttpSession session) {
        EmployeeLoginSdi employeeLoginSdi = request.get("employee");
        Optional<Employee> employee =  employeeRepo.findByEmail(employeeLoginSdi.getEmail());
        session.setAttribute("employee",employee);
        boolean isAuthen = false;
        if (employee.isPresent()){
            if(employee.get().getPassword().equals(employeeLoginSdi.getPassword())){
                isAuthen = true;
            }
        }

        if(!isAuthen){
            throw new NotFoundException("Email or password incorrect");
        }

        Map<String, EmployeeSelfSdo> warpper =new HashMap<>();
        EmployeeSelfSdo employeeSelfSdo = EmployeeSelfSdo.builder()
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
                .departmentId(employee.get().getDepartmentId()).build();

        employeeSelfSdo.setToken(jwtTokenUtil.generrateToken(employee.get(),24*60*60));
        warpper.put("user",employeeSelfSdo);
        return warpper;
    }
}
