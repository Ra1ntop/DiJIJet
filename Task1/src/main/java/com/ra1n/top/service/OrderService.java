package com.ra1n.top.service;

import com.ra1n.top.model.dto.OrderDto;

import java.util.List;

/**
 * @author Travkin Andrii
 * @version 29.03.2025
 */

public interface OrderService {

    OrderDto createOrder(OrderDto orderDTO);

    List<OrderDto> getOrdersByCustomer(String customerName);
}
