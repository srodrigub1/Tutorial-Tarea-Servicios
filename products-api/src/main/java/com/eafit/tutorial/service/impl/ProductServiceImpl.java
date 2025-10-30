package com.eafit.tutorial.service.impl;

import com.eafit.tutorial.dto.*;
import com.eafit.tutorial.exception.ProductAlreadyExistsException;
import com.eafit.tutorial.exception.ProductNotFoundException;
import com.eafit.tutorial.model.Product;
import com.eafit.tutorial.repository.ProductRepository;
import com.eafit.tutorial.service.ProductService;
import com.eafit.tutorial.util.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repo;

    public ProductServiceImpl(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public ProductDTO create(CreateProductDTO dto) {
        repo.findByNameIgnoreCase(dto.name()).ifPresent(p -> { throw new ProductAlreadyExistsException(dto.name()); });
        var p = new Product();
        p.setName(dto.name());
        p.setCategory(dto.category());
        p.setPrice(dto.price());
        p.setStock(dto.stock());
        return ProductMapper.toDTO(repo.save(p));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getById(Long id) {
        var p = repo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return ProductMapper.toDTO(p);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<ProductDTO> list(String name, String category, BigDecimal min, BigDecimal max, Pageable pageable) {
        Page<Product> page;
        if (name != null && !name.isBlank()) {
            page = repo.findByNameContainingIgnoreCase(name, pageable);
        } else if (category != null && !category.isBlank()) {
            page = repo.findByCategoryIgnoreCase(category, pageable);
        } else if (min != null && max != null) {
            page = repo.findByPriceBetween(min, max, pageable);
        } else {
            page = repo.findAll(pageable);
        }
        var content = page.getContent().stream().map(ProductMapper::toDTO).toList();
        return new PagedResponse<>(content, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public ProductDTO update(Long id, UpdateProductDTO dto) {
        var p = repo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        p.setCategory(dto.category());
        p.setPrice(dto.price());
        p.setStock(dto.stock());
        return ProductMapper.toDTO(repo.save(p));
    }

    @Override
    public ProductDTO patchStock(Long id, Integer stock) {
        var p = repo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        p.setStock(stock);
        return ProductMapper.toDTO(repo.save(p));
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ProductNotFoundException(id);
        repo.deleteById(id);
    }
}
