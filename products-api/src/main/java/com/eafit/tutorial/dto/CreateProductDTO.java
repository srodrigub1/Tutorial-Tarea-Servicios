package com.eafit.tutorial.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CreateProductDTO(
        @NotBlank String name,
        @NotBlank String category,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 10, fraction = 2) BigDecimal price,
        @NotNull @Min(0) Integer stock
) {}
