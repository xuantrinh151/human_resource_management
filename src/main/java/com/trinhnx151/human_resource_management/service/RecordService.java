package com.trinhnx151.human_resource_management.service;

import com.trinhnx151.human_resource_management.dto.sdi.record.RecordCreateSdi;
import com.trinhnx151.human_resource_management.dto.sdi.record.RecordSearchSdi;
import com.trinhnx151.human_resource_management.dto.sdi.record.RecordUpdateSdi;
import com.trinhnx151.human_resource_management.dto.sdo.record.RecordCreateSdo;
import com.trinhnx151.human_resource_management.dto.sdo.record.RecordSearchSdo;
import com.trinhnx151.human_resource_management.dto.sdo.record.RecordUpdateSdo;
import com.trinhnx151.human_resource_management.entity.Record;
import com.trinhnx151.human_resource_management.exception.custom.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RecordService {
    Page<RecordSearchSdo> search(RecordSearchSdi request, Pageable pageable);

    Optional<Record> findById(Long id) throws NotFoundException;

    RecordCreateSdo create(RecordCreateSdi request);

    RecordUpdateSdo update(RecordUpdateSdi request) throws Exception;

    Boolean deleteById(Long id);

    List<Record> getAllRecord();
}
