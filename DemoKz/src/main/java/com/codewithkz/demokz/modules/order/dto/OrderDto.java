package com.codewithkz.demokz.modules.order.dto;

import com.codewithkz.demokz.modules.product.dto.ProductDto;
import com.codewithkz.demokz.modules.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private UserDto user;
    private List<OrderItemDto> orderItems;
    private int totalPrice;
    private int totalQuantity;
}
