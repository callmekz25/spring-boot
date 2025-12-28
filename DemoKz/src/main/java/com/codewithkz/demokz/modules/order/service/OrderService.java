package com.codewithkz.demokz.modules.order.service;

import com.codewithkz.demokz.common.exception.BadRequestException;
import com.codewithkz.demokz.common.exception.NotFoundException;
import com.codewithkz.demokz.modules.order.dto.CreateOrderDto;
import com.codewithkz.demokz.modules.order.dto.OrderDto;
import com.codewithkz.demokz.modules.order.dto.ProductItemDto;
import com.codewithkz.demokz.modules.order.entity.Order;
import com.codewithkz.demokz.modules.order.entity.OrderItem;
import com.codewithkz.demokz.modules.order.mapper.OrderMapper;
import com.codewithkz.demokz.modules.order.repository.OrderRepository;
import com.codewithkz.demokz.modules.product.entity.Product;
import com.codewithkz.demokz.modules.product.service.ProductService;
import com.codewithkz.demokz.modules.user.entity.User;
import com.codewithkz.demokz.modules.user.service.UserService;
import jakarta.persistence.OptimisticLockException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service

public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductService productService;
    private final UserService userService;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, ProductService productService, UserService userService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.productService = productService;
        this.userService = userService;
    }

    public List<OrderDto> GetOrders() {
        List<Order> orders = orderRepository.findAll();

        return orderMapper.toListDto(orders);
    }

    @Transactional
    public OrderDto CreateOrder(CreateOrderDto dto) {

        Order order = new Order();

        order.setTotalPrice(0);
        order.setTotalQuantity(0);



        User user = userService.GetById(dto.getUserId());

        if(user == null) {
            throw new NotFoundException("User not found");
        }
        order.setUser(user);
        orderRepository.save(order);

        int totalQuantity = 0;
        int totalPrice = 0;

        List<OrderItem> orderItems = new ArrayList<>();


        for (ProductItemDto i : dto.getItems()) {

            Product product = productService.GetById(i.getId());

            if (product == null) {
                throw new NotFoundException("Product not found");
            }

            int quantity = i.getQuantity();
            int unitPrice = product.getPrice();

            if(product.getQuantity() < quantity) {
                throw new BadRequestException("Quantity is not enough");
            }

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setPrice(unitPrice);
            item.setOrder(order);

            product.setQuantity(product.getQuantity() - quantity);


            totalQuantity += quantity;
            totalPrice += unitPrice * quantity;

            orderItems.add(item);
        }
        order.setOrderItems(orderItems);
        order.setTotalQuantity(totalQuantity);
        order.setTotalPrice(totalPrice);

        orderRepository.save(order);

        return orderMapper.toDto(order);

    }

}
