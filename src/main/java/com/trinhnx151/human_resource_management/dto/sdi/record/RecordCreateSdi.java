package com.trinhnx151.human_resource_management.dto.sdi.record;

import com.trinhnx151.human_resource_management.entity.Department;
import com.trinhnx151.human_resource_management.entity.Record;
import com.trinhnx151.human_resource_management.util.Method;
import lombok.*;
import org.springframework.beans.BeanUtils;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class RecordCreateSdi {

    @NotNull
    private Long employeeId;

    @NotNull
    private boolean type;

    @Size(max = 500,message = "Không được dài quá 500 ký tự")
    private String reason;

    public Record toRecord() {
        Record record = new Record();
        BeanUtils.copyProperties(this, record);
        record.setStatus(Department.Status.IN_USE);
        record.setCreateTime(Method.getT_now());
        return record;
    }

}
