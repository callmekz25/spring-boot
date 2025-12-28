package com.codewithkz.demokz.modules.product.service;


import com.codewithkz.demokz.common.exception.BadRequestException;
import com.codewithkz.demokz.common.exception.NotFoundException;
import com.codewithkz.demokz.modules.product.dto.CreateProductDto;
import com.codewithkz.demokz.modules.product.dto.ProductDto;
import com.codewithkz.demokz.modules.product.entity.Product;
import com.codewithkz.demokz.modules.product.mapper.ProductMapper;
import com.codewithkz.demokz.modules.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

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
        return productRepository.findById(id).orElse(null);
    }

    public ProductDto GetProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            throw new NotFoundException("Product not found");
        }
        return productMapper.toDto(product);
    }

    public ProductDto CreateProduct(CreateProductDto dto) {
        try {
            Product product = productMapper.toEntity(dto);
            productRepository.save(product);

            return productMapper.toDto(product);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }
}
