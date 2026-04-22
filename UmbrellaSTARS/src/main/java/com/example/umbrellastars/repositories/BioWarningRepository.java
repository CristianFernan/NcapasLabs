package com.example.umbrellastars.repositories;

import com.example.umbrellastars.common.BioWarningList;
import com.example.umbrellastars.domain.model.BiologicalWarning;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BioWarningRepository {
    private final BioWarningList bioWarningList;

    public List<BiologicalWarning> findAll(){
        return bioWarningList.getBiologicalWarnings();
    }
}
