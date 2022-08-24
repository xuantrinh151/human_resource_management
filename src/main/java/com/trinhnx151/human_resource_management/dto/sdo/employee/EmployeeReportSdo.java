package com.trinhnx151.human_resource_management.dto.sdo.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeReportSdo {
    private String codeEmployee;
    private String nameEmployee;
    private Integer totalAchievement;
    private Integer totalDiscipline;
    private Integer totalPoint;
}
