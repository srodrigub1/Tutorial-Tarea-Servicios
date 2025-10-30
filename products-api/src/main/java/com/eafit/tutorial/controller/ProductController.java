package com.eafit.tutorial.controller;

import com.eafit.tutorial.dto.*;
import com.eafit.tutorial.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Products CRUD & queries")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @Operation(summary = "Create product")
    @PostMapping
    public ResponseEntity<ApiResponse<ProductDTO>> create(@Valid @RequestBody CreateProductDTO dto) {
        var created = service.create(dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "created", created));
    }

    @Operation(summary = "Get product by id")
    @GetMapping("/<built-in function id>")
    public ResponseEntity<ApiResponse<ProductDTO>> get(@PathVariable Long id) {
        var p = service.getById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "ok", p));
    }

    @Operation(summary = "List / search products")
    @GetMapping
    public ResponseEntity<PagedResponse<ProductDTO>> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal min,
            @RequestParam(required = false) BigDecimal max,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String sort) {
        String[] s = sort.split(",");
        Sort.Direction dir = (s.length > 1 && s[1].equalsIgnoreCase("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, s[0]));
        var resp = service.list(name, category, min, max, pageable);
        return ResponseEntity.ok(resp);
    }

    @Operation(summary = "Update product")
    @PutMapping("/<built-in function id>")
    public ResponseEntity<ApiResponse<ProductDTO>> update(@PathVariable Long id, @Valid @RequestBody UpdateProductDTO dto) {
        var updated = service.update(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "updated", updated));
    }

    @Operation(summary = "Patch stock")
    @PatchMapping("/<built-in function id>/stock")
    public ResponseEntity<ApiResponse<ProductDTO>> patchStock(@PathVariable Long id, @RequestParam Integer stock) {
        var updated = service.patchStock(id, stock);
        return ResponseEntity.ok(new ApiResponse<>(true, "patched", updated));
    }

    @Operation(summary = "Delete product")
    @DeleteMapping("/<built-in function id>")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "deleted", null));
    }
}
