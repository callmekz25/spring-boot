package com.codewithkz.demokz.modules.order.service;

import com.codewithkz.demokz.modules.order.dto.CreateOrderDto;
import com.codewithkz.demokz.modules.order.dto.OrderDto;

import java.util.List;

public interface IOrderService {
    List<OrderDto> GetOrders();
    OrderDto CreateOrder(CreateOrderDto dto);
}
