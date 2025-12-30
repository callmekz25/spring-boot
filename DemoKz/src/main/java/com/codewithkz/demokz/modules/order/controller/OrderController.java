package com.codewithkz.demokz.modules.order.controller;

import com.codewithkz.demokz.common.response.ApiResponse;
import com.codewithkz.demokz.modules.order.dto.CreateOrderDto;
import com.codewithkz.demokz.modules.order.dto.OrderDto;
import com.codewithkz.demokz.modules.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderDto>>> GetOrders() {
        List<OrderDto> orders = orderService.GetOrders();

        return ResponseEntity.ok(ApiResponse.success(orders));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderDto>> CreateOrder(@Valid @RequestBody CreateOrderDto dto) {
        OrderDto order = orderService.CreateOrder(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(order));
    }
}
