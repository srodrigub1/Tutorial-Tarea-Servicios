package com.eafit.tutorial.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDTO(Long id, String name, String category, BigDecimal price, Integer stock, LocalDateTime createdAt) {}
