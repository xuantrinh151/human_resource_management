package com.trinhnx151.human_resource_management.entity;

import lombok.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Record {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean type;
    private String reason;
    private Integer status;
    private Long employeeId;
    private Timestamp createTime;
    public interface Status {
        Integer CEASE_USING = 0;
        Integer IN_USE = 1;
        Integer DELETED = 2;
    }
}
