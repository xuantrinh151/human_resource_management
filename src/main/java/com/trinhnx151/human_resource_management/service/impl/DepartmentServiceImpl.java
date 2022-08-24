package com.trinhnx151.human_resource_management.service.impl;

import com.trinhnx151.human_resource_management.dto.sdi.department.DepartmentCreateSdi;
import com.trinhnx151.human_resource_management.dto.sdi.department.DepartmentSearchSdi;
import com.trinhnx151.human_resource_management.dto.sdi.department.DepartmentUpdateSdi;
import com.trinhnx151.human_resource_management.dto.sdo.department.DepartmentCreateSdo;
import com.trinhnx151.human_resource_management.dto.sdo.department.DepartmentSearchSdo;
import com.trinhnx151.human_resource_management.dto.sdo.department.DepartmentSelfSdo;
import com.trinhnx151.human_resource_management.dto.sdo.department.DepartmentUpdateSdo;
import com.trinhnx151.human_resource_management.entity.Department;
import com.trinhnx151.human_resource_management.exception.custom.DuplicateException;
import com.trinhnx151.human_resource_management.exception.custom.NotFoundException;
import com.trinhnx151.human_resource_management.repository.DepartmentRepo;
import com.trinhnx151.human_resource_management.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepo departmentRepo;

    @Override
    public Page<DepartmentSearchSdo> search(DepartmentSearchSdi request, Pageable pageable) {
        return departmentRepo.search(request, pageable);
    }

    @Override
    public List<Department> getAllDepartment() {
        return departmentRepo.findAll();
    }

    @Override
    public DepartmentSelfSdo findById(Long id) throws NotFoundException {
        Optional<Department> department = departmentRepo.findById(id);
        if (department.isPresent()){
            return DepartmentSelfSdo.builder()
                    .code(department.get().getCode())
                    .name(department.get().getName())
                    .status(department.get().getStatus())
                    .build();
        }else {
            throw new NotFoundException("Không tìm thấy phòng ban với id [" + id + "]");
        }
    }

    @Override
    public DepartmentCreateSdo create(DepartmentCreateSdi request) throws Exception {
        this.validCreate(request);
        Department department = request.toDepartment();
        departmentRepo.save(department);
        return DepartmentCreateSdo.builder()
                .id(department.getId())
                .build();
    }

    @Override
    public DepartmentUpdateSdo update(DepartmentUpdateSdi request) throws Exception {
        this.validUpdate(request);
        Department department = request.toDepartment();
        departmentRepo.save(department);
        return DepartmentUpdateSdo.builder()
                    .id(department.getId())
                    .build();
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Department> foundDepartment = departmentRepo.findById(id);
        if (foundDepartment.isPresent()) {
            foundDepartment.get().setStatus(2);
            departmentRepo.save(foundDepartment.get());
            return true;
        }
        return false;
    }

    private void validCreate(DepartmentCreateSdi request) {
        String code = request.getCode().toUpperCase();
        String name = request.getName().toUpperCase();
        Optional<Department> departmentByCode = departmentRepo.findByCode(code);
        if (departmentByCode.isPresent()) {
            throw new DuplicateException(("Ma phong ban [" + code + "] da ton tai"));
        }

        Optional<Department> departmentByName = departmentRepo.findByName(name);
        if (departmentByName.isPresent()) {
            throw new DuplicateException("Ten phong ban [" + name + "] da ton tai");
        }
    }

    private void validUpdate(DepartmentUpdateSdi request) throws Exception {
        Long id = request.getId();
        String code = request.getCode().toUpperCase();
        String name = request.getName().toUpperCase();
        Optional<Department> foundDepartment = departmentRepo.findByIdWithAllStatus(id);
        if (foundDepartment.isEmpty()) {
            throw new NotFoundException("Không tìm thấy phòng ban với id: [" + id + "]");
        }

        Optional<Department> departmentByCode = departmentRepo.findByCode(code,id);
        if (departmentByCode.isPresent()) {
            throw new DuplicateException("Ma phong ban [" + code + "] da ton tai");
        }

        Optional<Department> departmentByName = departmentRepo.findByName(name,id);
        if (departmentByName.isPresent()) {
            throw new DuplicateException("Ten phong ban [" + name + "] da ton tai");
        }
    }
}
