package org.example.lab4backend.repositories;

import org.example.lab4backend.entities.PlanPago;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlanPagoRepository extends JpaRepository<PlanPago, Integer> {
    List<PlanPago> findByPrestamoIdOrderByNumeroCuota(Integer prestamoId);
}
