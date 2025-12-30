package com.codewithkz.demokz.modules.product.service;


import com.codewithkz.demokz.common.exception.NotFoundException;
import com.codewithkz.demokz.modules.product.dto.CreateProductDto;
import com.codewithkz.demokz.modules.product.dto.ProductDto;
import com.codewithkz.demokz.modules.product.entity.Product;
import com.codewithkz.demokz.modules.product.mapper.ProductMapper;
import com.codewithkz.demokz.modules.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDto> GetProducts() {
        List<Product> products = productRepository.findAll();
        return productMapper.toDtoList(products);
    }

    public Product GetById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> {
            log.warn("Product not found, productId={}", id);
            return new NotFoundException("Product not found");
        });
    }

    public ProductDto GetProduct(Long id) {
        Product product = this.GetById(id);
        return productMapper.toDto(product);
    }

    public ProductDto CreateProduct(CreateProductDto dto) {
        Product product = productMapper.toEntity(dto);
        productRepository.save(product);

        log.info("Created product: {}", product);
        return productMapper.toDto(product);

    }


    public List<ProductDto> GetProductsOutOfStock() {
        List<Product> products = productRepository.findProductsOutOfStock();
        return productMapper.toDtoList(products);
    }
}
