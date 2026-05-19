package org.example.sheikaregistry.repositories;

import org.example.sheikaregistry.domain.entities.Specimen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpecimenRepository extends JpaRepository<Specimen, UUID> {
    Specimen findSpecimenById(UUID id);
}
