package com.codewithkz.demokz.modules.order.mapper;

import com.codewithkz.demokz.modules.order.dto.CreateOrderDto;
import com.codewithkz.demokz.modules.order.dto.OrderDto;
import com.codewithkz.demokz.modules.order.entity.Order;
import com.codewithkz.demokz.modules.user.mapper.UserMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",  uses = { OrderItemMapper.class, UserMapper.class })
public interface OrderMapper {
    OrderDto toDto(Order order);
    List<OrderDto> toListDto(List<Order> orders);
}
