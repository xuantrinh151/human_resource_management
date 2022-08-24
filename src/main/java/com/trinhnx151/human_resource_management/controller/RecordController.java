package com.trinhnx151.human_resource_management.controller;

import com.trinhnx151.human_resource_management.dto.sdi.record.RecordCreateSdi;
import com.trinhnx151.human_resource_management.dto.sdi.record.RecordSearchSdi;
import com.trinhnx151.human_resource_management.dto.sdi.record.RecordUpdateSdi;
import com.trinhnx151.human_resource_management.dto.sdo.record.RecordCreateSdo;
import com.trinhnx151.human_resource_management.dto.sdo.record.RecordSearchSdo;
import com.trinhnx151.human_resource_management.dto.sdo.record.RecordUpdateSdo;
import com.trinhnx151.human_resource_management.entity.Record;
import com.trinhnx151.human_resource_management.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/record")
public class RecordController {

    private final RecordService recordService;

    @GetMapping("/search")
    Page<RecordSearchSdo> getDepartmentByCodeOrName(RecordSearchSdi request,Pageable pageable) {
        return recordService.search(request, pageable);
    }

    @GetMapping("")
    List<Record> getAllRecord() {
        return recordService.getAllRecord();
    }

    @GetMapping("/{id}")
    Optional<Record> findRecordById(@PathVariable Long id) {
        return recordService.findById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    RecordCreateSdo create(@RequestBody @Valid RecordCreateSdi request) throws Exception {
        return recordService.create(request);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    RecordUpdateSdo update(@RequestBody @Valid RecordUpdateSdi request) throws Exception {
        return recordService.update(request);
    }

    @DeleteMapping("/{id}/delete")
    Boolean deleteRecord(@PathVariable Long id) {
        return recordService.deleteById(id);
    }
}
