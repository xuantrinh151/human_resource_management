package com.trinhnx151.human_resource_management.dto.sdi.record;

import com.trinhnx151.human_resource_management.entity.Record;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecordUpdateSdi {

    @NotNull
    private Long id;
    @NotNull
    private boolean type;

    @Size(max = 500,message = "Không được dài quá 500 ký tự")
    private String reason;

    @NotNull
    @Min(value = 0,message = "Trạng thái gồm: 0-Không hoạt động, 1-Đang hoạt động, 2-Đã xóa")
    @Max(value = 2,message = "Trạng thái gồm: 0-Không hoạt động, 1-Đang hoạt động, 2-Đã xóa")
    private Integer status;
    @NotNull
    private Long employeeId;

    public Record toRecord() {
        Record record = new Record();
        BeanUtils.copyProperties(this, record);
        return record;
    }
}
