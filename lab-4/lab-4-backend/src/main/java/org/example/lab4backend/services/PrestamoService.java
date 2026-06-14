package org.example.lab4backend.services;


import org.example.lab4backend.dto.request.AbonoRequest;
import org.example.lab4backend.dto.request.PrestamoRequest;
import org.example.lab4backend.dto.response.PageableResponse;
import org.example.lab4backend.dto.response.PlanPagoResponse;
import org.example.lab4backend.dto.response.PrestamoResponse;
import org.example.lab4backend.entities.Abono;
import org.example.lab4backend.entities.PlanPago;
import org.example.lab4backend.entities.Prestamo;
import org.example.lab4backend.entities.User;
import org.example.lab4backend.exceptions.ResourceNotFoundException;
import org.example.lab4backend.repositories.AbonoRepository;
import org.example.lab4backend.repositories.PlanPagoRepository;
import org.example.lab4backend.repositories.PrestamoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final PlanPagoRepository planPagoRepository;
    private final AbonoRepository abonoRepository;

    public PrestamoService(PrestamoRepository prestamoRepository,
                           PlanPagoRepository planPagoRepository,
                           AbonoRepository abonoRepository) {
        this.prestamoRepository = prestamoRepository;
        this.planPagoRepository = planPagoRepository;
        this.abonoRepository = abonoRepository;
    }

    /** Crea préstamo y genera plan de amortización automáticamente */
    @Transactional
    public PrestamoResponse crear(PrestamoRequest req, User usuario) {
        Prestamo prestamo = new Prestamo();
        prestamo.setCapitalSolicitado(req.getCapitalSolicitado());
        prestamo.setTasaInteresAnual(req.getTasaInteresAnual());
        prestamo.setPlazoMeses(req.getPlazoMeses());
        prestamo.setEstado(Prestamo.EstadoPrestamo.PENDIENTE);
        prestamo.setUsuario(usuario);
        prestamo = prestamoRepository.save(prestamo);

        generarPlanAmortizacion(prestamo);
        return toResponse(prestamo);
    }

    /** Amortización (cuota fija) */
    private void generarPlanAmortizacion(Prestamo prestamo) {
        BigDecimal capital = prestamo.getCapitalSolicitado();
        BigDecimal tasaAnual = prestamo.getTasaInteresAnual();
        int meses = prestamo.getPlazoMeses();

        // Tasa mensual
        BigDecimal tasaMensual = tasaAnual.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        // Cuota fija: C = P * [r(1+r)^n] / [(1+r)^n - 1]
        BigDecimal unoPlusR = BigDecimal.ONE.add(tasaMensual);
        BigDecimal potencia = unoPlusR.pow(meses, new MathContext(10));
        BigDecimal cuota = capital
                .multiply(tasaMensual.multiply(potencia))
                .divide(potencia.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);

        BigDecimal saldo = capital;
        List<PlanPago> cuotas = new ArrayList<>();

        for (int i = 1; i <= meses; i++) {
            BigDecimal interes = saldo.multiply(tasaMensual).setScale(2, RoundingMode.HALF_UP);
            BigDecimal capitalCuota = cuota.subtract(interes).setScale(2, RoundingMode.HALF_UP);
            if (i == meses) capitalCuota = saldo; // ajuste última cuota

            PlanPago pp = new PlanPago();
            pp.setNumeroCuota(i);
            pp.setMontoCapital(capitalCuota);
            pp.setMontoInteres(interes);
            pp.setFechaVencimiento(LocalDate.now().plusMonths(i));
            pp.setEstado(PlanPago.EstadoCuota.PENDIENTE);
            pp.setPrestamo(prestamo);
            cuotas.add(pp);

            saldo = saldo.subtract(capitalCuota);
        }
        planPagoRepository.saveAll(cuotas);
    }

    public PageableResponse<PrestamoResponse> listar(int page, int size) {
        Page<Prestamo> result = prestamoRepository.findAll(PageRequest.of(page, size));
        List<PrestamoResponse> content = result.getContent().stream().map(this::toResponse).toList();
            return new PageableResponse<>(content, page, size, result.getTotalElements(),
                result.getTotalPages(), result.isLast());
    }

    public PageableResponse<PrestamoResponse> listarPorUsuario(Integer usuarioId, int page, int size) {
        Page<Prestamo> result = prestamoRepository.findByUsuarioId(usuarioId, PageRequest.of(page, size));
        List<PrestamoResponse> content = result.getContent().stream().map(this::toResponse).toList();
        return new PageableResponse<>(content, page, size, result.getTotalElements(),
                result.getTotalPages(), result.isLast());
    }

    public List<PlanPagoResponse> obtenerPlan(Integer prestamoId) {
        return planPagoRepository.findByPrestamoIdOrderByNumeroCuota(prestamoId)
                .stream().map(this::toPlanResponse).toList();
    }

    @Transactional
    public void aprobar(Integer prestamoId) {
        Prestamo p = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new ResourceNotFoundException("Préstamo no encontrado"));
        p.setEstado(Prestamo.EstadoPrestamo.APROBADO);
        prestamoRepository.save(p);
    }

    @Transactional
    public Abono registrarAbono(Integer planPagoId, AbonoRequest req) {
        PlanPago pp = planPagoRepository.findById(planPagoId)
                .orElseThrow(() -> new ResourceNotFoundException("Préstamo no encontrado"));

        // Calcular mora si está vencida
        BigDecimal mora = BigDecimal.ZERO;
        if (LocalDate.now().isAfter(pp.getFechaVencimiento())) {
            long diasMora = LocalDate.now().toEpochDay() - pp.getFechaVencimiento().toEpochDay();
            mora = pp.getMontoCapital().add(pp.getMontoInteres())
                    .multiply(BigDecimal.valueOf(0.001))   // 0.1% diario
                    .multiply(BigDecimal.valueOf(diasMora))
                    .setScale(2, RoundingMode.HALF_UP);
        }

        Abono abono = new Abono();
        abono.setMonto(req.getMonto());
        abono.setFechaPago(LocalDate.now());
        abono.setRecargoMora(mora);
        abono.setPlanPago(pp);
        abonoRepository.save(abono);

        pp.setEstado(PlanPago.EstadoCuota.PAGADO);
        planPagoRepository.save(pp);

        return abono;
    }

    private PrestamoResponse toResponse(Prestamo p) {
        PrestamoResponse r = new PrestamoResponse();
        r.setId(p.getId());
        r.setCapitalSolicitado(p.getCapitalSolicitado());
        r.setTasaInteresAnual(p.getTasaInteresAnual());
        r.setPlazoMeses(p.getPlazoMeses());
        r.setEstado(p.getEstado());
        r.setUsuarioUsername(p.getUsuario().getUsername());
        return r;
    }

    private PlanPagoResponse toPlanResponse(PlanPago pp) {
        PlanPagoResponse r = new PlanPagoResponse();
        r.setId(pp.getId());
        r.setNumeroCuota(pp.getNumeroCuota());
        r.setMontoCapital(pp.getMontoCapital());
        r.setMontoInteres(pp.getMontoInteres());
        r.setMontoTotal(pp.getMontoCapital().add(pp.getMontoInteres()));
        r.setFechaVencimiento(pp.getFechaVencimiento());
        r.setEstado(pp.getEstado());
        return r;
    }
}