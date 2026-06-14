package org.example.lab4backend.repositories;

import org.example.lab4backend.entities.Prestamo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {
    Page<Prestamo> findByUsuarioId(Integer usuarioId, Pageable pageable);
}
