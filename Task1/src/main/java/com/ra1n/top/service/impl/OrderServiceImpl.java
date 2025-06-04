package com.ra1n.top.service.impl;

import com.ra1n.top.model.dto.OrderDto;
import com.ra1n.top.model.entity.Order;
import com.ra1n.top.output.persistent.OrderRepository;
import com.ra1n.top.service.OrderService;
import com.ra1n.top.service.mappers.OrderMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Travkin Andrii
 * @version 29.03.2025
 */
@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper = OrderMapper.INSTANCE;

    @Override
    public OrderDto createOrder(OrderDto orderDTO) {
        if (orderDTO == null
                || orderDTO.getCustomerName() == null || orderDTO.getCustomerName().isBlank()
                || orderDTO.getOrderItems() == null || orderDTO.getOrderItems().isEmpty()) {
            throw new com.ra1n.top.model.exception.Ra1nException(
                    "Invalid order data", org.springframework.http.HttpStatus.BAD_REQUEST
            );
        }
        Order order = orderMapper.orderDTOToOrder(orderDTO);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.orderToOrderDTO(savedOrder);
    }

    @Override
    public List<OrderDto> getOrdersByCustomer(String customerName) {
        if (customerName == null || customerName.isBlank()) {
            throw new com.ra1n.top.model.exception.Ra1nException(
                    "Customer name must not be empty", org.springframework.http.HttpStatus.BAD_REQUEST
            );
        }
        List<Order> orders = orderRepository.findByCustomerName(customerName);

        return orders.stream()
                .map(orderMapper::orderToOrderDTO)
                .toList();
    }
}
