package com.trinhnx151.human_resource_management.dto.sdi.department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentReportSdi {
    private String from;
    private String to;
}
