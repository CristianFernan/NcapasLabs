package org.example.midpiece.repository;

import org.example.midpiece.domain.entity.Pirate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PirateRepository extends JpaRepository<Pirate, UUID> {

    Pirate getPirateById(UUID id);
}
