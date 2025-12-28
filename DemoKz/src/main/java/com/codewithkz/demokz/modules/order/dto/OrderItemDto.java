package com.codewithkz.demokz.modules.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private Long id;
    private Long orderId;
    private Long productId;
    private int quantity;
    private int price;
    private String name;
}
