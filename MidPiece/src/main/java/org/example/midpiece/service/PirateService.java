package org.example.midpiece.service;

import org.example.midpiece.domain.entity.Pirate;

import java.util.List;
import java.util.UUID;

public interface PirateService {
    // crear pirata
    void createPirate(Pirate pirate);
    // obtener todos los piratas
    List<Pirate> getAll();
    // obtener pirata por Id
    Pirate getPirateById(UUID id);
    // actualizar pirata
    void updatePirate(UUID id, Pirate pirate);
    // borrar pirata
    Pirate deletePirate(UUID id);
}
