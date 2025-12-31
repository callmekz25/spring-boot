package com.codewithkz.demokz.modules.product;

import com.codewithkz.demokz.common.exception.BadRequestException;
import com.codewithkz.demokz.common.exception.NotFoundException;
import com.codewithkz.demokz.modules.product.controller.ProductController;
import com.codewithkz.demokz.modules.product.dto.CreateProductDto;
import com.codewithkz.demokz.modules.product.dto.ProductDto;
import com.codewithkz.demokz.modules.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    public void shouldReturnOkWhenGetProducts() throws Exception {
        var productsDto = List.of(
                new ProductDto(1L,"Test1",10, 1000),
                new ProductDto(2L,"Test2",10, 1000),
                new ProductDto(3L,"Test3",10, 1000)
        );

        Mockito.when(productService.GetProducts()).thenReturn(productsDto);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(3))
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[0].name").value("Test1"))
                .andExpect(jsonPath("$.data[0].price").value(1000))
                .andExpect(jsonPath("$.data[0].quantity").value(10))
                .andDo(print());
    }

    @Test
    public void shouldReturnOkWhenGetProduct() throws Exception {
        var dto = new ProductDto(1L,"Test1",10, 1000);

        Mockito.when(productService.GetProduct(1L)).thenReturn(dto);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("Test1"))
                .andExpect(jsonPath("$.data.price").value(1000))
                .andExpect(jsonPath("$.data.quantity").value(10))
                .andDo(print());
    }

    @Test
    public void shouldReturnNotFoundWhenGetProduct() throws Exception {


        Mockito.when(productService.GetProduct(100000L)).thenThrow(new NotFoundException("Product not found"));

        mockMvc.perform(get("/products/100000"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.message").value("Product not found"))
                .andDo(print());
    }

    @Test
    public void shouldReturnCreatedWhenCreateProduct() throws Exception {

        var dto = new CreateProductDto("Test", 1000, 10);

        var productDto = new ProductDto(1L, "Test", 10, 1000);

        Mockito.when(productService.CreateProduct(Mockito.any(CreateProductDto.class))).thenReturn(productDto);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("Test"))
                .andExpect(jsonPath("$.data.price").value(1000))
                .andExpect(jsonPath("$.data.quantity").value(10))
                .andDo(print());
    }

    @Test
    public void shouldReturn400WhenCreateProduct() throws Exception {

        var dto = new CreateProductDto("", 1000, 10);


        Mockito.when(productService.CreateProduct(Mockito.any(CreateProductDto.class)))
                .thenThrow(new BadRequestException("Name must not be empty"));

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.message").value("Name must not be empty"))
                .andDo(print());
    }


}
