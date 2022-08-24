package com.trinhnx151.human_resource_management.dto.sdi.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeReportSdi {
    private String from;
    private String to;
}
