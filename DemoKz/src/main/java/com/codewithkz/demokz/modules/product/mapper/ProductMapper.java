package com.codewithkz.demokz.modules.product.mapper;

import com.codewithkz.demokz.modules.product.dto.ProductDto;
import com.codewithkz.demokz.modules.product.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);
    Product toEntity(ProductDto productDto);
    List<ProductDto> toDtoList(List<Product> products);
}
