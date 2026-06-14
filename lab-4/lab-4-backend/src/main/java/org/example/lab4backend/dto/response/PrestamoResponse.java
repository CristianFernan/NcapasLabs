package org.example.lab4backend.dto.response;

import lombok.Data;
import org.example.lab4backend.entities.Prestamo.EstadoPrestamo;

import java.math.BigDecimal;

@Data
public class PrestamoResponse {
    private Integer id;
    private BigDecimal capitalSolicitado;
    private BigDecimal tasaInteresAnual;
    private Integer plazoMeses;
    private EstadoPrestamo estado;
    private String usuarioUsername;
}