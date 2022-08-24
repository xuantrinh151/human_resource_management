package com.trinhnx151.human_resource_management.dto.sdo.department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentReportSdo {
    private String codeDepartment;
    private String nameDepartment;
    private Integer totalAchievement;
    private Integer totalDiscipline;
    private Integer totalPoint;
}
