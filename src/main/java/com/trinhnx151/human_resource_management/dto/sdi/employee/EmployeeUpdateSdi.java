package com.trinhnx151.human_resource_management.dto.sdi.employee;

import com.sun.istack.NotNull;
import com.trinhnx151.human_resource_management.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import javax.validation.constraints.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeUpdateSdi {
    private long id;
    @NotNull
    @NotEmpty(message = "Code không được để trống")
    @Size(max = 50,message = "Code không quá 50 ký tự")
    private String code;

    @NotNull
    @NotEmpty(message = "Name không được để trống")
    @Size(max = 255,message = "Name không quá 255 ký tự")
    private String name;

    @NotNull
    private boolean gender;

    @Size(max = 500,message = "Không được quá 500 ký tự")
    private String image;


    private Timestamp dob;
    @NotNull
    @Min(value = 0,message = "Lương phải lớn hoặc bằng 0")
    private double salary;

    @Min(value = 1,message = "Level phải từ 1 đến 10")
    @Max(value = 10,message = "Level phải từ 1 đến 10")
    private int level;

    @NotNull
    @NotEmpty(message = "Email không được để trống")
    @Size(max = 100,message = "Email không quá 100 ký tự")
    @Email(message = "Sai định dạng email")
    private String email;

    @NotNull
    @NotEmpty(message = "Phone không được để trống")
    @Size(max = 15,message = "Phone không quá 15 ký tự")
    private String phone;

    @NotNull
    @Min(value = 0,message = "Trạng thái gồm: 0-Không hoạt động, 1-Đang hoạt động, 2-Đã xóa")
    @Max(value = 2,message = "Trạng thái gồm: 0-Không hoạt động, 1-Đang hoạt động, 2-Đã xóa")
    private Integer status;

    @NotNull
    private Long departmentId;

    public Employee toEmployee() {
        Employee employee = new Employee();
        BeanUtils.copyProperties(this, employee);
        return employee;
    }
}
