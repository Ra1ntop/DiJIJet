package com.ra1n.top.model.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Travkin Andrii
 * @version 29.03.2025
 */
@Data
public class OrderItemDto {

    private String menuItemId;
    private int quantity;
    private BigDecimal price;
}
