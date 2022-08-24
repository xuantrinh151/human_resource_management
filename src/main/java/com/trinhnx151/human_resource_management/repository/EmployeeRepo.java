package com.trinhnx151.human_resource_management.repository;

import com.trinhnx151.human_resource_management.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee,Long>,EmployeeRepoCustom {
    @Query(
            value = "SELECT * FROM EMPLOYEE WHERE STATUS <> 2 AND ID = :id",
            nativeQuery = true
    )
    Optional<Employee> findById(Long id);

    @Query(
            value = "SELECT * FROM EMPLOYEE WHERE STATUS <> 2 AND CODE = :code",
            nativeQuery = true
    )
    Optional<Employee> findByCode(String code);

    @Query(
            value = "SELECT * FROM EMPLOYEE WHERE STATUS <> 2 AND CODE = :code AND ID <> :ignoreId",
            nativeQuery = true
    )
    Optional<Employee> findByCode(String code,Long ignoreId);


    @Query(
            value = "SELECT * FROM EMPLOYEE WHERE STATUS <> 2 AND PHONE = :phone",
            nativeQuery = true
    )
    Optional<Employee> findByPhone(String phone);

    @Query(
            value = "SELECT * FROM EMPLOYEE WHERE STATUS <> 2 AND PHONE = :phone AND ID <> :ignoreId",
            nativeQuery = true
    )
    Optional<Employee> findByPhone(String phone,Long ignoreId);

    @Query(
            value = "SELECT * FROM EMPLOYEE WHERE STATUS <> 2 AND EMAIL = :email",
            nativeQuery = true
    )
    Optional<Employee> findByEmail(String email);

    @Query(
            value = "SELECT * FROM EMPLOYEE WHERE STATUS <> 2 AND EMAIL = :email AND ID <> :ignoreId",
            nativeQuery = true
    )
    Optional<Employee> findByEmail(String email,Long ignoreId);

    @Query(
            value = "SELECT * FROM EMPLOYEE WHERE ID = :id",
            nativeQuery = true
    )
    Optional<Employee> findByIdWithAllStatus(Long id);
}
