package com.ra1n.top.controller;

import com.ra1n.top.model.dto.MenuItemDto;
import com.ra1n.top.model.entity.MenuItem;
import com.ra1n.top.output.persistent.MenuItemRepository;
import com.ra1n.top.service.MenuItemService;
import com.ra1n.top.service.mappers.MenuItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Travkin Andrii
 * @version 29.03.2025
 */

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuItemController {
    private final MenuItemService menuItemService;

    @GetMapping("/{cafeId}")
    public ResponseEntity<List<MenuItemDto>> getMenuByCafeId(@PathVariable String cafeId) {
       return ResponseEntity.ok(menuItemService.getMenuByCafeId(cafeId));
    }

    @PostMapping
    public ResponseEntity<MenuItemDto> addMenuItem(@RequestBody MenuItemDto menuItemDTO) {
        return ResponseEntity.ok(menuItemService.addMenuItem(menuItemDTO));
    }
}
