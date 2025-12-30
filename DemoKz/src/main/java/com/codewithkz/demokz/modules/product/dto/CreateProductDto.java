package com.codewithkz.demokz.modules.product.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDto {
    @NotBlank(message = "Name must not be empty")
    private String name;
    @NotBlank(message = "Price must not be empty")
    @Size(min = 1, message = "Price must be min 1")
    private int price;
    @NotBlank(message = "Quantity must not be empty")
    @Size(min = 1, max = 1000, message = "Quantity must be between 1 and 1000")
    private int quantity;
}
