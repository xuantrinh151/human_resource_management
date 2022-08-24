package com.trinhnx151.human_resource_management.dto.sdi.department;

import com.sun.istack.NotNull;
import com.trinhnx151.human_resource_management.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentUpdateSdi {
    private Long id;

    @NotNull
    @NotEmpty(message = "Code không được để trống")
    @Size(max = 50,message = "Code không quá 50 ký tự")
    private String code;

    @NotNull
    @NotEmpty(message = "Name không được để trống")
    @Size(max = 255,message = "Name không quá 255 ký tự")
    private String name;

    @NotNull
    @Min(value = 0,message = "Trạng thái gồm: 0-Không hoạt động, 1-Đang hoạt động, 2-Đã xóa")
    @Max(value = 2,message = "Trạng thái gồm: 0-Không hoạt động, 1-Đang hoạt động, 2-Đã xóa")
    private Integer status;

    public Department toDepartment() {
        Department department = new Department();
        BeanUtils.copyProperties(this, department);
        return department;
    }

}
