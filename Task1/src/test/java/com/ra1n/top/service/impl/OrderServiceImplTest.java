package com.ra1n.top.service.impl;

import com.ra1n.top.model.dto.OrderDto;
import com.ra1n.top.model.dto.OrderItemDto;
import com.ra1n.top.model.entity.Order;
import com.ra1n.top.model.entity.OrderItem;
import com.ra1n.top.model.exception.Ra1nException;
import com.ra1n.top.output.persistent.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void createOrder_invalidData_throwsException() {
        Ra1nException ex = assertThrows(Ra1nException.class, () -> orderService.createOrder(null));
        assertEquals("Invalid order data", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    void createOrder_validData_savesAndReturnsDto() {
        OrderDto dto = new OrderDto();
        dto.setCustomerName("John");
        dto.setCafeId("c1");
        OrderItemDto itemDto = new OrderItemDto();
        itemDto.setMenuItemId("m1");
        itemDto.setQuantity(2);
        itemDto.setPrice(BigDecimal.valueOf(5));
        dto.setOrderItems(Arrays.asList(itemDto));

        OrderItem item = OrderItem.builder()
                .menuItemId("m1").quantity(2).price(BigDecimal.valueOf(5)).build();
        Order saved = Order.builder()
                .id("1").customerName("John").cafeId("c1").orderItems(Arrays.asList(item)).build();
        when(orderRepository.save(any(Order.class))).thenReturn(saved);
        OrderDto result = orderService.createOrder(dto);
        assertEquals("1", result.getId());
        assertEquals("John", result.getCustomerName());
    }

    @Test
    void getOrdersByCustomer_nullOrBlank_throwsException() {
        Ra1nException ex1 = assertThrows(Ra1nException.class, () -> orderService.getOrdersByCustomer(null));
        assertEquals("Customer name must not be empty", ex1.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex1.getStatus());
    }

    @Test
    void getOrdersByCustomer_returnsMappedList() {
        Order order1 = Order.builder().id("1").customerName("Jane").cafeId("c1").build();
        Order order2 = Order.builder().id("2").customerName("Jane").cafeId("c1").build();
        when(orderRepository.findByCustomerName("Jane")).thenReturn(Arrays.asList(order1, order2));
        List<OrderDto> dtos = orderService.getOrdersByCustomer("Jane");
        assertEquals(2, dtos.size());
        assertEquals("1", dtos.get(0).getId());
        assertEquals("2", dtos.get(1).getId());
    }
}