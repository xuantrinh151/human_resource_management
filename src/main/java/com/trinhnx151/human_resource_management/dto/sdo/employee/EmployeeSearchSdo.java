package com.trinhnx151.human_resource_management.dto.sdo.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSearchSdo {
    private long id;
    private String code;
    private String fullName;
    private boolean gender;
    private String email;
    private String phone;
    private Integer status;
}
