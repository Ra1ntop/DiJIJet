package com.ra1n.top.service.impl;

import com.ra1n.top.model.dto.MenuItemDto;
import com.ra1n.top.model.entity.MenuItem;
import com.ra1n.top.model.enums.MenuCategoryEnum;
import com.ra1n.top.model.exception.Ra1nException;
import com.ra1n.top.output.persistent.MenuItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuItemServiceImplTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @InjectMocks
    private MenuItemServiceImpl menuItemService;

    @Test
    void getMenuByCafeId_nullOrBlank_throwsException() {
        Ra1nException ex1 = assertThrows(Ra1nException.class, () -> menuItemService.getMenuByCafeId(null));
        assertEquals("Cafe id must not be empty", ex1.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex1.getStatus());
        Ra1nException ex2 = assertThrows(Ra1nException.class, () -> menuItemService.getMenuByCafeId(" "));
        assertEquals("Cafe id must not be empty", ex2.getMessage());
    }

    @Test
    void getMenuByCafeId_returnsMappedList() {
        MenuItem item1 = MenuItem.builder()
                .id("1").cafeId("c1").name("Item1").price(BigDecimal.valueOf(5)).category(MenuCategoryEnum.DESSERTS).build();
        MenuItem item2 = MenuItem.builder()
                .id("2").cafeId("c1").name("Item2").price(BigDecimal.valueOf(10)).category(MenuCategoryEnum.MAIN_COURSES).build();
        when(menuItemRepository.findAllByCafeId("c1")).thenReturn(Arrays.asList(item1, item2));
        List<MenuItemDto> dtos = menuItemService.getMenuByCafeId("c1");
        assertEquals(2, dtos.size());
        assertEquals("Item1", dtos.get(0).getName());
        assertEquals(MenuCategoryEnum.MAIN_COURSES, dtos.get(1).getCategory());
    }

    @Test
    void addMenuItem_invalidData_throwsException() {
        Ra1nException ex = assertThrows(Ra1nException.class, () -> menuItemService.addMenuItem(new MenuItemDto()));
        assertEquals("Invalid menu item data", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    void addMenuItem_validData_savesAndReturnsDto() {
        MenuItemDto dto = new MenuItemDto();
        dto.setCafeId("c1");
        dto.setName("Item");
        dto.setPrice(BigDecimal.valueOf(3));
        dto.setCategory(MenuCategoryEnum.MAIN_COURSES);

        MenuItem saved = MenuItem.builder()
                .id("1").cafeId("c1").name("Item").price(BigDecimal.valueOf(3)).category(MenuCategoryEnum.MAIN_COURSES).build();
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(saved);
        MenuItemDto result = menuItemService.addMenuItem(dto);
        assertEquals("1", result.getId());
        assertEquals("Item", result.getName());
    }
}