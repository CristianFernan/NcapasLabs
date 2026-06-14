package org.example.lab4backend.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "Plan_cagos")
public class PlanPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "numero_cuota", nullable = false)
    private Integer numeroCuota;

    @Column(name = "monto_capital", nullable = false, precision = 12, scale = 2)
    private BigDecimal montoCapital;

    @Column(name = "monto_interes", nullable = false, precision = 12, scale = 2)
    private BigDecimal montoInteres;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCuota estado = EstadoCuota.PENDIENTE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestamo_id", nullable = false)
    private Prestamo prestamo;

    @OneToMany(mappedBy = "planPago", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Abono> abonos;

    public enum EstadoCuota { PENDIENTE, PAGADO }
}