package com.trinhnx151.human_resource_management.dto.sdo.record;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordSearchSdo {
    private long id;
    private String codeEmployee;
    private String nameEmployee;
    private String reason;
    private Integer status;
}
