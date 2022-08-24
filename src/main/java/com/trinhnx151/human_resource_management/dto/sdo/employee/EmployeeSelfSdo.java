package com.trinhnx151.human_resource_management.dto.sdo.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeSelfSdo {
    private long id;
    private String code;
    private String fullName;
    private boolean gender;
    private String image;
    private Timestamp dob;
    private double salary;
    private int level;
    private String email;
    private String phone;
    private Integer status;
    private Long departmentId;
}
