package com.codewithkz.demokz.modules.product.service;

import com.codewithkz.demokz.modules.product.dto.CreateProductDto;
import com.codewithkz.demokz.modules.product.dto.ProductDto;
import com.codewithkz.demokz.modules.product.entity.Product;

import java.util.List;

public interface IProductService {
    List<ProductDto> GetProducts();
    Product GetById(Long id);
    ProductDto GetProduct(Long id);
    ProductDto CreateProduct(CreateProductDto dto);
}
