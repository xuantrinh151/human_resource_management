package com.trinhnx151.human_resource_management.dto.sdi.department;

import com.sun.istack.NotNull;
import com.trinhnx151.human_resource_management.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentCreateSdi {

    @javax.validation.constraints.NotNull(message = "is required")
    @NotEmpty(message = "Code không được để trống")
    @Size(max = 50,message = "Code không quá 50 ký tự")
    private String code;

    @NotNull
    @NotEmpty(message = "Name không được để trống")
    @Size(max = 255,message = "Name không quá 255 ký tự")
    private String name;

    public Department toDepartment() {
        Department department = new Department();
        BeanUtils.copyProperties(this, department);
        department.setStatus(Department.Status.IN_USE);
        return department;
    }


}
