package com.trinhnx151.human_resource_management.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseOject {
    private String status;
    private String message;
    private Object data;
}
