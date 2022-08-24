package com.trinhnx151.human_resource_management.repository;

import com.trinhnx151.human_resource_management.dto.sdi.record.RecordSearchSdi;
import com.trinhnx151.human_resource_management.dto.sdo.record.RecordSearchSdo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecordRepoCustom {

    Page<RecordSearchSdo> search(RecordSearchSdi request, Pageable pageable);
}
