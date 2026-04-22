package com.example.umbrellastars.services;

import com.example.umbrellastars.domain.model.BiologicalWarning;
import com.example.umbrellastars.repositories.BioWarningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BioWarningService {
    private final BioWarningRepository bioWarningRepository;

    public List<BiologicalWarning> findAll(){
        return  bioWarningRepository.findAll();
    }

    //Filtrado por virus base
    public List<BiologicalWarning> findByBaseVirus(String baseVirus){
        return bioWarningRepository.findAll()
                .stream()
                .filter(obj -> obj.getBaseVirus()
                        .equals(baseVirus))
                .toList();
    }
    //Filtrado por estado actual
    public List<BiologicalWarning> findByCurrentState(String currentState){
        return bioWarningRepository.findAll()
                .stream()
                .filter(obj -> obj.getCurrentState()
                        .equals(currentState))
                .toList();
    }

    //Virus sin repetición (usar distinct)
    public List<BiologicalWarning> findDistinctVirus(){
        return bioWarningRepository.findAll()
                .stream()
                .filter(obj -> obj.getBaseVirus()
                        .equals("Free"))
                .distinct()
                .toList();
    }

}
