package com.codewithkz.demokz.modules.order;

import com.codewithkz.demokz.modules.order.dto.CreateOrderDto;
import com.codewithkz.demokz.modules.order.dto.OrderDto;
import com.codewithkz.demokz.modules.order.dto.OrderItemDto;
import com.codewithkz.demokz.modules.order.dto.ProductItemDto;
import com.codewithkz.demokz.modules.order.entity.Order;
import com.codewithkz.demokz.modules.order.entity.OrderItem;
import com.codewithkz.demokz.modules.order.mapper.OrderMapper;
import com.codewithkz.demokz.modules.order.repository.OrderRepository;
import com.codewithkz.demokz.modules.order.service.OrderService;
import com.codewithkz.demokz.modules.product.entity.Product;
import com.codewithkz.demokz.modules.product.service.ProductService;
import com.codewithkz.demokz.modules.user.dto.UserDto;
import com.codewithkz.demokz.modules.user.entity.Roles;
import com.codewithkz.demokz.modules.user.entity.User;
import com.codewithkz.demokz.modules.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderService orderService;


    public static Order createOrderMock() {
        User user = new User(1L, "Nguyen Van A", "a@gmail.com", "123", Roles.ROLE_USER);

        Product p1 = new Product(1L, "Chair", 10, 1000, 0L);
        Product p2 = new Product(2L, "Table", 5, 2000, 0L);

        OrderItem i1 = new OrderItem(1L, 2, 1000, null, p1);
        OrderItem i2 = new OrderItem(2L, 1, 2000, null, p2);

        Order order = new Order(1L, 4000, 3, List.of(i1, i2), user);

        i1.setOrder(order);
        i2.setOrder(order);

        return order;
    }

    public static OrderDto createOrderDtoMock() {
        var user = new UserDto(1L, "Nguyen Van A", "a@gmail.com");


        var i1 = new OrderItemDto(1L, 1L, 1L, 2, 100, "Laptop Acer");
        var i2 = new OrderItemDto(2L, 1L, 2L, 2, 200, "Laptop Dell");

        var order = new OrderDto(1L, user, List.of(i1, i2), 600, 4);


        return order;
    }


    @Test
    public void shouldReturnOrders() {

        var orders = List.of(createOrderMock());

        var dtos = List.of(createOrderDtoMock());

        Mockito.when(orderRepository.findAll()).thenReturn(orders);
        Mockito.when(orderMapper.toListDto(orders)).thenReturn(dtos);

        var result = orderService.GetOrders();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(dtos, result);

        Mockito.verify(orderRepository).findAll();
        Mockito.verify(orderMapper).toListDto(orders);
    }

    @Test
    public void shouldCreateOrder() {
        var items = List.of(
                new ProductItemDto(1L, 1),
                new ProductItemDto(2L, 2)
        );
        var dto = new CreateOrderDto(items, 1L);

        User user = new User(1L, "Nguyen Van A", "a@gmail.com", "123", Roles.ROLE_USER);

        var userDto = new UserDto(1L, "Nguyen Van A", "a@g;mail.com");

        var i1 = new OrderItemDto(1L, 1L, 1L, 2, 100, "Laptop Acer");
        var i2 = new OrderItemDto(2L, 1L, 2L, 2, 200, "Laptop Dell");


        Product product1 = new Product(1L, "Chair", 10, 100, 0L);
        Product product2 = new Product(2L, "Table", 5, 200, 0L);

        Mockito.when(userService.GetById(1L))
                .thenReturn(user);
        Mockito.when(productService.GetById(1L))
                .thenReturn(product1);
        Mockito.when(productService.GetById(2L))
                .thenReturn(product2);

        Mockito.when(orderRepository.save(Mockito.any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Mockito.when(orderMapper.toDto(Mockito.any(Order.class)))
                .thenReturn(new OrderDto(1L, userDto, List.of(i1, i2), 600, 4));



        var result = orderService.CreateOrder(dto);

        assertNotNull(result);
        assertEquals(600, result.getTotalPrice());
        assertEquals(4, result.getTotalQuantity());
        assertEquals(1L, result.getUser().getId());

        Mockito.verify(userService).GetById(1L);
        Mockito.verify(productService).GetById(1L);
        Mockito.verify(productService).GetById(2L);

        Mockito.verify(orderRepository, Mockito.times(2)).save(Mockito.any(Order.class));
        Mockito.verify(orderMapper).toDto(Mockito.any(Order.class));


    }
}
