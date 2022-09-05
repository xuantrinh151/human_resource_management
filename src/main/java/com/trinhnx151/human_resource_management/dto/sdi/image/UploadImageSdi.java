package com.trinhnx151.human_resource_management.dto.sdi.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadImageSdi {
    private String base64;
    private String name;
}
