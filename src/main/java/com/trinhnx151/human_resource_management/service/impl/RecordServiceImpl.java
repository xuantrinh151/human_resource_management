package com.trinhnx151.human_resource_management.service.impl;

import com.trinhnx151.human_resource_management.dto.sdi.record.RecordCreateSdi;
import com.trinhnx151.human_resource_management.dto.sdi.record.RecordSearchSdi;
import com.trinhnx151.human_resource_management.dto.sdi.record.RecordUpdateSdi;
import com.trinhnx151.human_resource_management.dto.sdo.record.RecordCreateSdo;
import com.trinhnx151.human_resource_management.dto.sdo.record.RecordSearchSdo;
import com.trinhnx151.human_resource_management.dto.sdo.record.RecordUpdateSdo;
import com.trinhnx151.human_resource_management.entity.Employee;
import com.trinhnx151.human_resource_management.entity.Record;
import com.trinhnx151.human_resource_management.exception.custom.NotFoundException;
import com.trinhnx151.human_resource_management.repository.EmployeeRepo;
import com.trinhnx151.human_resource_management.repository.RecordRepo;
import com.trinhnx151.human_resource_management.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {
    private final RecordRepo recordRepo;
    private final EmployeeRepo employeeRepo;

    @Override
    public Page<RecordSearchSdo> search(RecordSearchSdi request, Pageable pageable) {
        return recordRepo.search(request,pageable);
    }

    @Override
    public RecordCreateSdo create(RecordCreateSdi request) {
        this.validateCreate(request);
        Record record = request.toRecord();
        recordRepo.save(record);
        return RecordCreateSdo.builder()
                .id(record.getId())
                .build();
    }

    private void validateCreate(RecordCreateSdi request) {
        Long employeeId = request.getEmployeeId();
        Optional<Employee> foundEmployee = employeeRepo.findById(employeeId);
        if (foundEmployee.isEmpty()) {
            throw new NotFoundException("Không tìm thấy nhân viên với id: [" + employeeId + "]");
        }
    }

    @Override
    public List<Record> getAllRecord() {
        return recordRepo.findAll();
    }

    @Override
    public Optional<Record> findById(Long id) throws NotFoundException {

        Optional<Record> record = recordRepo.findById(id);
        if (record.isPresent()){
            return record;
        }else {
            throw new NotFoundException("Không tìm thấy bản ghi với id [" + id + "]");
        }
    }

    @Override
    public RecordUpdateSdo update(RecordUpdateSdi request) {
        this.validateUpdate(request);
        Record record = request.toRecord();
        recordRepo.save(record);
        return RecordUpdateSdo.builder()
                    .id(record.getId())
                    .build();
    }

    private void validateUpdate(RecordUpdateSdi request) {
        Long id = request.getId();
        Long employeeId = request.getEmployeeId();
        Optional<Employee> foundEmployee = employeeRepo.findById(employeeId);
        if (foundEmployee.isEmpty()) {
            throw new NotFoundException("Không tìm thấy nhân viên với id: [" + employeeId + "]");
        }
        Optional<Record> foundRecord = recordRepo.findByIdWithAllStatus(id);
        if (foundRecord.isEmpty()) {
            throw new NotFoundException("Không tìm thấy bản ghi với id: [" + id + "]");
        }
    }

    @Override
    public Boolean deleteById(Long id) {
        Optional<Record> foundRecord = recordRepo.findById(id);
        if (foundRecord.isPresent()) {
            foundRecord.get().setStatus(2);
            recordRepo.save(foundRecord.get());
            return true;
        }
        return false;
    }


}
