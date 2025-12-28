package com.codewithkz.demokz.modules.order.mapper;


import com.codewithkz.demokz.modules.order.dto.OrderItemDto;
import com.codewithkz.demokz.modules.order.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "name")
    OrderItemDto toDto(OrderItem orderItem);
}
