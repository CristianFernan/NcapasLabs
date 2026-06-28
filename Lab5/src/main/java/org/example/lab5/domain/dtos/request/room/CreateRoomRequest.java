package org.example.lab5.domain.dtos.request.room;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomRequest {
    @NotBlank(message = "room name cannot be empty")
    private String name;

    @NotBlank(message = "room description cannot be empty")
    private String description;

    @NotBlank(message = "room capacity cannot be empty")
    private Integer capacity;

    @NotBlank(message = "room location cannot be empty")
    private String location;

    private Boolean active;
}
