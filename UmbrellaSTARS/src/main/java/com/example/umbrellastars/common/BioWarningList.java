package com.example.umbrellastars.common;

import com.example.umbrellastars.domain.model.BiologicalWarning;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BioWarningList {
    private final List<BiologicalWarning> biologicalWarnings;

    public BioWarningList() {
        this.biologicalWarnings = new ArrayList<>();

        this.biologicalWarnings.add(BiologicalWarning.builder()
                .id(1)
                .name("Licker")
                .baseVirus("T-virus")
                .riskLevel(3)
                .weakPoint("arms")
                .currentState("Contained")
                .lastSeen("Laboratory")
                .originPlace("DEI")
                .build());

        this.biologicalWarnings.add(BiologicalWarning.builder()
                .id(2)
                .name("Spitter")
                .baseVirus("Caduo")
                .riskLevel(2)
                .weakPoint("stomach")
                .currentState("Eliminated")
                .lastSeen("Commissary")
                .originPlace("Magna IV")
                .build());

        this.biologicalWarnings.add(BiologicalWarning.builder()
                .id(4)
                .name("Smoker")
                .baseVirus("G-virus")
                .riskLevel(5)
                .weakPoint("tongue")
                .currentState("Free")
                .lastSeen("Town")
                .originPlace("Comissary")
                .build());


        this.biologicalWarnings.add(BiologicalWarning.builder()
                .id(3)
                .name("Hunter")
                .baseVirus("Plague")
                .riskLevel(5)
                .weakPoint("head")
                .currentState("Free")
                .lastSeen("Town")
                .originPlace("Cafeteria")
                .build());

        this.biologicalWarnings.add(BiologicalWarning.builder()
                .id(4)
                .name("Tyrant")
                .baseVirus("G-virus")
                .riskLevel(5)
                .weakPoint("chest")
                .currentState("Free")
                .lastSeen("Mansion")
                .originPlace("Building-B")
                .build());


    }

    public List<BiologicalWarning> getBiologicalWarnings() { return biologicalWarnings; }
}

