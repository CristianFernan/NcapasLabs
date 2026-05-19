package org.example.sheikaregistry.domain.dto.response;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
public class ApiErrorResponse {
    private String uri;
    private Object message;
    private int status;
    private LocalDate time;
}
