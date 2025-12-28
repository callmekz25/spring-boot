package com.codewithkz.demokz.modules.order.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductItemDto {
    private Long id;
    private int quantity;
}
