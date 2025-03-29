package com.ra1n.top.service;

import com.ra1n.top.model.dto.MenuItemDto;

import java.util.List;

/**
 * @author Travkin Andrii
 * @version 29.03.2025
 */
public interface MenuItemService {

    List<MenuItemDto> getMenuByCafeId(String cafeId);

    MenuItemDto addMenuItem(MenuItemDto menuItemDTO);
}
