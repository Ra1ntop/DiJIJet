package com.ra1n.top.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Travkin Andrii
 * @version 29.03.2025
 */
@Data
public class OrderDto {

    private String id;
    private String customerName;
    private BigDecimal totalAmount;
    private String cafeId;
    private List<OrderItemDto> orderItems;
}
