package org.example.lab4backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PrestamoRequest {

    @NotNull
    @DecimalMin("100.00")
    private BigDecimal capitalSolicitado;

    @NotNull
    @DecimalMin("0.01")
    @DecimalMax("1.00")
    private BigDecimal tasaInteresAnual;

    @NotNull
    @Min(1) @Max(360)
    private Integer plazoMeses;
}