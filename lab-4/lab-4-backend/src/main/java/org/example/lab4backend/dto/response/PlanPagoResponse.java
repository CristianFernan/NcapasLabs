package org.example.lab4backend.dto.response;

import lombok.Data;
import org.example.lab4backend.entities.PlanPago.EstadoCuota;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PlanPagoResponse {
    private Integer id;
    private Integer numeroCuota;
    private BigDecimal montoCapital;
    private BigDecimal montoInteres;
    private BigDecimal montoTotal;
    private LocalDate fechaVencimiento;
    private EstadoCuota estado;
}