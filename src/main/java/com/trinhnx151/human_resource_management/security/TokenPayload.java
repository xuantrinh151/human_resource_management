package com.trinhnx151.human_resource_management.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenPayload {
    private Long id;
    private String email;

}
