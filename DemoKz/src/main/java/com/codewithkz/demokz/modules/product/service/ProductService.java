package com.codewithkz.demokz.modules.product.service;


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
}
