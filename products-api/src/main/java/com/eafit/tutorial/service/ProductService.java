package com.eafit.tutorial.service;

import com.eafit.tutorial.dto.*;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface ProductService {
    ProductDTO create(CreateProductDTO dto);
    ProductDTO getById(Long id);
    PagedResponse<ProductDTO> list(String name, String category, BigDecimal min, BigDecimal max, Pageable pageable);
    ProductDTO update(Long id, UpdateProductDTO dto);
    ProductDTO patchStock(Long id, Integer stock);
    void delete(Long id);
}
