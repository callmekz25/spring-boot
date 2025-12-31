package com.codewithkz.demokz.modules.product.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDto {
    @NotBlank(message = "Name must not be empty")
    private String name;
    @NotNull(message = "Price must not be empty")
    @Min(value = 1, message = "Price must be min 1")
    private int price;
    @NotNull(message = "Quantity must not be empty")
    @Min(value = 1, message = "Quantity must be between 1 and 1000")
    private int quantity;
}
