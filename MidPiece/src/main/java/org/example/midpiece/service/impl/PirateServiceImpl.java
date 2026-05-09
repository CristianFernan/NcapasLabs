package org.example.midpiece.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.midpiece.domain.entity.Pirate;
import org.example.midpiece.repository.PirateRepository;
import org.example.midpiece.service.PirateService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PirateServiceImpl implements PirateService {
    private final PirateRepository pirateRepository;

    @Override
    public void createPirate(Pirate pirate) {
        pirateRepository.save(pirate);
    }

    @Override
    public List<Pirate> getAll(){
        return pirateRepository.findAll();
    }

    @Override
    public Pirate getPirateById(UUID id){
        return pirateRepository.getPirateById(id);
    }

    @Override
    public void updatePirate(UUID id, Pirate pirate){
        Pirate existPirate = pirateRepository.getPirateById(id);
        existPirate.setName(pirate.getName());
        existPirate.setBounty(pirate.getBounty());
        existPirate.setIsAlive(pirate.getIsAlive());
        existPirate.setCrew(pirate.getCrew());
        pirateRepository.save(existPirate);
    }

    @Override
    public Pirate deletePirate(UUID id){
        Pirate existPirate = pirateRepository.getPirateById(id);
        pirateRepository.deleteById(id);
        return existPirate;
    }
}
