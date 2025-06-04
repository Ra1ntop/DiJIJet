package com.ra1n.top.service.impl;

import com.ra1n.top.model.dto.MenuItemDto;
import com.ra1n.top.model.entity.MenuItem;
import com.ra1n.top.model.exception.Ra1nException;
import com.ra1n.top.output.persistent.MenuItemRepository;
import com.ra1n.top.service.MenuItemService;
import com.ra1n.top.service.mappers.MenuItemMapper;
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
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;

    private final MenuItemMapper menuItemMapper = MenuItemMapper.INSTANCE;

    @Override
    public List<MenuItemDto> getMenuByCafeId(String cafeId) {
        if (cafeId == null || cafeId.isBlank()) {
            throw new Ra1nException(
                    "Cafe id must not be empty", org.springframework.http.HttpStatus.BAD_REQUEST
            );
        }
        List<MenuItem> menuItems = menuItemRepository.findAllByCafeId(cafeId);
        return menuItemMapper.menuItemsToMenuItemDTOs(menuItems);
    }

    @Override
    public MenuItemDto addMenuItem(MenuItemDto menuItemDTO) {
        if (menuItemDTO == null
                || menuItemDTO.getName() == null || menuItemDTO.getName().isBlank()
                || menuItemDTO.getPrice() == null || menuItemDTO.getPrice().signum() < 0
                || menuItemDTO.getCategory() == null) {
            throw new Ra1nException(
                    "Invalid menu item data", org.springframework.http.HttpStatus.BAD_REQUEST
            );
        }
        MenuItem menuItem = menuItemMapper.menuItemDTOToMenuItem(menuItemDTO);

        MenuItem savedMenuItem = menuItemRepository.save(menuItem);

        return menuItemMapper.menuItemToMenuItemDTO(savedMenuItem);
    }
}
