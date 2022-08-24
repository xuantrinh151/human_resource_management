package com.trinhnx151.human_resource_management.dto.sdo.department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentSearchSdo {
    private Long id;
    private String code;
    private String name;
    private Integer status;
}
