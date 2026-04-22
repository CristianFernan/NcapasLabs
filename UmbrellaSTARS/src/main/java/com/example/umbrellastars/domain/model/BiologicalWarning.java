package com.example.umbrellastars.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BiologicalWarning {
    private int id;
    private String name;
    private String baseVirus;
    private int riskLevel;
    private String weakPoint;
    private String currentState;
    private String lastSeen;
    private String originPlace;
}
