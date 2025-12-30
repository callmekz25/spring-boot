package com.codewithkz.demokz.modules.order.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDto {
    @NotEmpty(message = "Product must not be empty")
    private List<ProductItemDto> items;
    @NotEmpty(message = "User Id must not be empty")
    private Long userId;
}
