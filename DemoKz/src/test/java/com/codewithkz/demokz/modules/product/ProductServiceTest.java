package com.codewithkz.demokz.modules.product;


import com.codewithkz.demokz.modules.product.dto.CreateProductDto;
import com.codewithkz.demokz.modules.product.dto.ProductDto;
import com.codewithkz.demokz.modules.product.entity.Product;
import com.codewithkz.demokz.modules.product.mapper.ProductMapper;
import com.codewithkz.demokz.modules.product.repository.ProductRepository;
import com.codewithkz.demokz.modules.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    public void shouldReturnProductsWhenGetProducts() {
        var products = List.of(
                new Product(1L,"Test1",10, 1000, 0L),
                new Product(2L,"Test2",10, 1000, 0L),
                new Product(3L,"Test3",10, 1000, 0L)
        );

        var productsDto = List.of(
                new ProductDto(1L,"Test1",10, 1000),
                new ProductDto(2L,"Test2",10, 1000),
                new ProductDto(3L,"Test3",10, 1000)
        );

        Mockito.when(productRepository.findAll()).thenReturn(products);
        Mockito.when(productMapper.toDtoList(products)).thenReturn(productsDto);

        List<ProductDto> result = productService.GetProducts();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(1L, result.getFirst().getId());
        assertEquals("Test1", result.getFirst().getName());
        assertEquals(10, result.getFirst().getQuantity());
        assertEquals(1000, result.getFirst().getPrice());

        Mockito.verify(productRepository).findAll();
        Mockito.verify(productMapper).toDtoList(products);

    }

    @Test
    public void shouldReturnProductWhenGetProduct() {
        var product = new Product(1L,"Test1",10, 1000, 0L);

        var productDto = new ProductDto(1L,"Test1",10, 1000);

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(productMapper.toDto(product)).thenReturn(productDto);

        ProductDto result = productService.GetProduct(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test1", result.getName());
        assertEquals(10, result.getQuantity());
        assertEquals(1000, result.getPrice());

        Mockito.verify(productRepository).findById(1L);
        Mockito.verify(productMapper).toDto(product);
    }

    @Test
    public void shouldReturnNotFoundWhenGetProduct() {


        Mockito.when(productRepository.findById(40L)).thenReturn(Optional.empty());


        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> productService.GetProduct(40L)
        );


        assertEquals("Product not found", exception.getMessage());

        Mockito.verify(productRepository).findById(40L);

    }

    @Test
    public void shouldCreateProduct() {
        var dto = new CreateProductDto("Test1",1000, 10);

        var entity = new Product(1L, "Test1", 10, 1000, 0L);

        var finalDto = new ProductDto(1L, "Test1", 10, 1000);

        Mockito.when(productMapper.toEntity(dto)).thenReturn(entity);
        Mockito.when(productRepository.save(entity)).thenReturn(entity);
        Mockito.when(productMapper.toDto(entity)).thenReturn(finalDto);

        ProductDto result = productService.CreateProduct(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test1", result.getName());
        assertEquals(10, result.getQuantity());
        assertEquals(1000, result.getPrice());

        Mockito.verify(productMapper).toEntity(dto);
        Mockito.verify(productRepository).save(entity);
        Mockito.verify(productMapper).toDto(entity);
    }


}
