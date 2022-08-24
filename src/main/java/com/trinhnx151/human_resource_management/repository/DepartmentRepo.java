package com.trinhnx151.human_resource_management.repository;

import com.trinhnx151.human_resource_management.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface DepartmentRepo extends JpaRepository<Department,Long>, DepartmentRepoCustom{
    @Query(
            value = "SELECT * FROM DEPARTMENT WHERE STATUS <> 2 AND ID = :id",
            nativeQuery = true
    )
    Optional<Department> findById(Long id);

    @Query(
            value = "SELECT * FROM DEPARTMENT WHERE ID = :id",
            nativeQuery = true
    )
    Optional<Department> findByIdWithAllStatus(Long id);

    @Query(
            value = "SELECT * FROM DEPARTMENT WHERE STATUS <> 2 AND UPPER(CODE) = :code",
            nativeQuery = true
    )
    Optional<Department> findByCode(String code);

    @Query(
            value = "SELECT * FROM DEPARTMENT WHERE STATUS <> 2 AND UPPER(CODE) = :code AND ID <> :ignoreId",
            nativeQuery = true
    )
    Optional<Department> findByCode(String code, Long ignoreId);

    @Query(
            value = "SELECT * FROM DEPARTMENT WHERE STATUS <> 2 AND UPPER(NAME) = :name",
            nativeQuery = true
    )
    Optional<Department> findByName(String name);

    @Query(
            value = "SELECT * FROM DEPARTMENT WHERE STATUS <> 2 AND UPPER(NAME) = :name AND ID <> :ignoreId",
            nativeQuery = true
    )
    Optional<Department> findByName(String name,Long ignoreId);
}
