package com.trinhnx151.human_resource_management.controller;

import com.trinhnx151.human_resource_management.dto.sdi.employee.EmployeeCreateSdi;
import com.trinhnx151.human_resource_management.dto.sdi.employee.EmployeeLoginSdi;
import com.trinhnx151.human_resource_management.dto.sdi.employee.EmployeeSearchSdi;
import com.trinhnx151.human_resource_management.dto.sdi.employee.EmployeeUpdateSdi;
import com.trinhnx151.human_resource_management.dto.sdi.image.UploadImageSdi;
import com.trinhnx151.human_resource_management.dto.sdo.employee.EmployeeCreateSdo;
import com.trinhnx151.human_resource_management.dto.sdo.employee.EmployeeSearchSdo;
import com.trinhnx151.human_resource_management.dto.sdo.employee.EmployeeSelfSdo;
import com.trinhnx151.human_resource_management.dto.sdo.employee.EmployeeUpdateSdo;
import com.trinhnx151.human_resource_management.entity.Employee;
import com.trinhnx151.human_resource_management.service.EmployeeService;
import com.trinhnx151.human_resource_management.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final StorageService storageService;


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

    @PostMapping("/create")
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

    @PostMapping("/login")
    public Map<String, EmployeeSelfSdo> login(@RequestBody Map<String, EmployeeLoginSdi> request, HttpSession session) {
        return employeeService.login(request, session);
    }

    @PostMapping("/upload")
    public String fileUpload(@RequestBody UploadImageSdi uploadImageSdi) throws IOException {
        //return storageService.storeFile(file, session);
        //return storageService.uploadFileWithBase64(file,session);
        return storageService.upload(uploadImageSdi);
    }

    @GetMapping("files/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
        try {
            byte[] bytes = storageService.readFileContent(fileName);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        } catch (Exception ex){
            return ResponseEntity.noContent().build();
        }
    }
}
