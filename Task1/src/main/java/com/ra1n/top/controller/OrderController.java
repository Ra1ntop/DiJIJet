package com.ra1n.top.controller;

import com.ra1n.top.model.dto.OrderDto;
import com.ra1n.top.model.entity.Order;
import com.ra1n.top.model.exception.Ra1nException;
import com.ra1n.top.output.persistent.OrderRepository;
import com.ra1n.top.service.OrderService;
import com.ra1n.top.service.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Travkin Andrii
 * @version 29.03.2025
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public OrderDto createOrder(@RequestBody OrderDto orderDTO) {
        if (orderDTO == null
                || orderDTO.getCustomerName() == null || orderDTO.getCustomerName().isBlank()
                || orderDTO.getOrderItems() == null || orderDTO.getOrderItems().isEmpty()) {
            throw new Ra1nException(
                    "Invalid order data", org.springframework.http.HttpStatus.BAD_REQUEST
            );
        }
        return orderService.createOrder(orderDTO);
    }

    @GetMapping("/by-customer")
    public List<OrderDto> getOrdersByCustomer(@RequestParam String customerName) {
        if (customerName == null || customerName.isBlank()) {
            throw new Ra1nException(
                    "Customer name must not be empty", org.springframework.http.HttpStatus.BAD_REQUEST
            );
        }
        return orderService.getOrdersByCustomer(customerName);
    }
}
