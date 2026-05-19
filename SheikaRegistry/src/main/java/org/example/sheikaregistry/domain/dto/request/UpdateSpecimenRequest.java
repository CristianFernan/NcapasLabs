package org.example.sheikaregistry.domain.dto.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSpecimenRequest {
    private String name;
    private String region;
    private Integer dangerLevel;
    private Boolean isFriendly;
}