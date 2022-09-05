package com.trinhnx151.human_resource_management.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private boolean gender;
    private String image;
    private Timestamp dob;
    private double salary;
    private int level;
    private String email;
    private String password;
    private String phone;
    private Integer status;
    private Long departmentId;
    //private Long roleId;

    public interface Status {
        Integer CEASE_USING = 0;
        Integer IN_USE = 1;
        Integer DELETED = 2;
    }
    public interface Level {
        Integer LEVEL_1 = 1;
        Integer LEVEL_2 = 2;
        Integer LEVEL_3 = 3;
        Integer LEVEL_4 = 4;
        Integer LEVEL_5 = 5;
        Integer LEVEL_6 = 6;
        Integer LEVEL_7 = 7;
        Integer LEVEL_8 = 8;
        Integer LEVEL_9 = 9;
        Integer LEVEL_10 = 10;
    }
}
