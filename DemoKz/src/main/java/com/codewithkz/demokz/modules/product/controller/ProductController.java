package com.codewithkz.demokz.modules.product.controller;


import com.codewithkz.demokz.common.response.ApiResponse;
import com.codewithkz.demokz.modules.product.dto.CreateProductDto;
import com.codewithkz.demokz.modules.product.dto.ProductDto;
import com.codewithkz.demokz.modules.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDto>>> GetProducts() {
        List<ProductDto> products = productService.GetProducts();

        return ResponseEntity.ok(ApiResponse.success(products));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<ProductDto>> GetProduct(@PathVariable Long id) {
        ProductDto product = productService.GetProduct(id);


        return ResponseEntity.ok(ApiResponse.success(product));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductDto>> CreateProduct(@Valid @RequestBody CreateProductDto dto) {
        ProductDto product = productService.CreateProduct(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(product));
    }
}
