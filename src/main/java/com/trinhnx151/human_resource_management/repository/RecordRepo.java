package com.trinhnx151.human_resource_management.repository;

import com.trinhnx151.human_resource_management.entity.Employee;
import com.trinhnx151.human_resource_management.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RecordRepo extends JpaRepository<Record,Long> ,RecordRepoCustom{

    @Query(
            value = "SELECT * FROM RECORD  WHERE STATUS <> 2 AND ID = :id",
            nativeQuery = true
    )
    Optional<Record> findById(Long id);

    @Query(
            value = "SELECT * FROM RECORD  WHERE ID = :id",
            nativeQuery = true
    )
    Optional<Record> findByIdWithAllStatus(Long id);
}
