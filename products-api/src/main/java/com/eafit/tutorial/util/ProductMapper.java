package com.eafit.tutorial.util;

import com.eafit.tutorial.dto.ProductDTO;
import com.eafit.tutorial.model.Product;

public class ProductMapper {
    public static ProductDTO toDTO(Product p) {
        return new ProductDTO(p.getId(), p.getName(), p.getCategory(), p.getPrice(), p.getStock(), p.getCreatedAt());
    }
}
