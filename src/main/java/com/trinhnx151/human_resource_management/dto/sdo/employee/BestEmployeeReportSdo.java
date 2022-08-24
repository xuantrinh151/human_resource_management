package com.trinhnx151.human_resource_management.dto.sdo.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BestEmployeeReportSdo {
    private String codeEmployee;
    private String nameEmployee;
    private String image;
    private Integer totalPoint;
}
