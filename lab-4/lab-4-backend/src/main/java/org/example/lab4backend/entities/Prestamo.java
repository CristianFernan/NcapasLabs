package org.example.lab4backend.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "Prestamo")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "capital_solicitado", nullable = false, precision = 12, scale = 2)
    private BigDecimal capitalSolicitado;

    @Column(name = "tasa_interes_anual", nullable = false, precision = 5, scale = 4)
    private BigDecimal tasaInteresAnual;

    @Column(name = "plazo_meses", nullable = false)
    private Integer plazoMeses;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPrestamo estado = EstadoPrestamo.PENDIENTE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;

    @OneToMany(mappedBy = "prestamo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlanPago> planPagos;

    public enum EstadoPrestamo { APROBADO, PENDIENTE, PAGADO }
}