package com.ra1n.top.service.mappers;

import com.ra1n.top.model.dto.MenuItemDto;
import com.ra1n.top.model.entity.MenuItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Travkin Andrii
 * @version 29.03.2025
 */
@Mapper
public interface MenuItemMapper {

    MenuItemMapper INSTANCE = Mappers.getMapper(MenuItemMapper.class);

    MenuItemDto menuItemToMenuItemDTO(MenuItem menuItem);

    MenuItem menuItemDTOToMenuItem(MenuItemDto menuItemDTO);

    List<MenuItemDto> menuItemsToMenuItemDTOs(List<MenuItem> menuItems);
}
