package com.ra1n.top.service.impl;

import com.ra1n.top.model.dto.MenuItemDto;
import com.ra1n.top.model.entity.MenuItem;
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
        List<MenuItem> menuItems = menuItemRepository.findAllByCafeId(cafeId);
        return menuItemMapper.menuItemsToMenuItemDTOs(menuItems);
    }

    @Override
    public MenuItemDto addMenuItem(MenuItemDto menuItemDTO) {
        MenuItem menuItem = menuItemMapper.menuItemDTOToMenuItem(menuItemDTO);

        MenuItem savedMenuItem = menuItemRepository.save(menuItem);

        return menuItemMapper.menuItemToMenuItemDTO(savedMenuItem);
    }
}
