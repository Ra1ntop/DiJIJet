package com.ra1n.top.model.dto;

import com.ra1n.top.model.enums.MenuCategoryEnum;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Travkin Andrii
 * @version 29.03.2025
 */
@Data
public class MenuItemDto {
    private String id;

    private String cafeId;

    private String name;

    private BigDecimal price;

    private MenuCategoryEnum category;
}