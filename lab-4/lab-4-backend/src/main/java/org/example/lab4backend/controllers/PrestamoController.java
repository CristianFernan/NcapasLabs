package org.example.lab4backend.controllers;

import jakarta.validation.Valid;
import org.example.lab4backend.dto.request.AbonoRequest;
import org.example.lab4backend.dto.request.PrestamoRequest;
import org.example.lab4backend.dto.response.PageableResponse;
import org.example.lab4backend.dto.response.PlanPagoResponse;
import org.example.lab4backend.dto.response.PrestamoResponse;
import org.example.lab4backend.entities.User;
import org.example.lab4backend.services.PrestamoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finanzas/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping
    public ResponseEntity<PrestamoResponse> crear(@Valid @RequestBody PrestamoRequest req,
                                                  @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(prestamoService.crear(req, user));
    }

    @GetMapping
    public ResponseEntity<PageableResponse<PrestamoResponse>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(prestamoService.listar(page, size));
    }

    @GetMapping("/mis-prestamos")
    public ResponseEntity<PageableResponse<PrestamoResponse>> misPrestamos(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(prestamoService.listarPorUsuario(user.getId(), page, size));
    }

    @GetMapping("/{id}/planes-pago")
    public ResponseEntity<List<PlanPagoResponse>> verPlan(@PathVariable Integer id) {
        return ResponseEntity.ok(prestamoService.obtenerPlan(id));
    }

}