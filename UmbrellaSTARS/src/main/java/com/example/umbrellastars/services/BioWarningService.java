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

}
