package org.example.midpiece.repository;

import org.example.midpiece.domain.entity.Pirate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Pirate, UUID> { }
