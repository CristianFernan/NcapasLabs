package org.example.lab5.domain.dtos.response.room;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RoomResponse {
    private UUID id;
    private String name;
    private String description;
    private Integer capacity;
    private String location;
    private Boolean active;
}
