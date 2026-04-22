package com.example.umbrellastars.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BiologicalWarning)) return false;
        BiologicalWarning that = (BiologicalWarning) o;
        return Objects.equals(baseVirus, that.baseVirus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseVirus);
    }
}
