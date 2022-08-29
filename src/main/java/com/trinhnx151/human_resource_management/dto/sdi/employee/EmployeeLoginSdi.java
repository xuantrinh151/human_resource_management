package com.trinhnx151.human_resource_management.dto.sdi.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeLoginSdi {
    private String email;
    private String password;
}
