package com.ra1n.top.controller;

import com.ra1n.top.model.dto.CafeDto;
import com.ra1n.top.model.exception.Ra1nException;
import com.ra1n.top.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/cafe")
@RequiredArgsConstructor
public class CafeController {

    private final CafeService cafeService;

    @GetMapping("/cafes")
    public List<CafeDto> getAllCafes(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        if (page < 0 || size <= 0) {
            throw new Ra1nException(
                    "Invalid pagination parameters: page=" + page + ", size=" + size,
                    HttpStatus.BAD_REQUEST
            );
        }
        Pageable pageable = PageRequest.of(page, size);
        return cafeService.getAllCafe(pageable);
    }

    @PostMapping
    public ResponseEntity<CafeDto> addCafe(@RequestBody CafeDto cafeDto) {
        if (cafeDto == null
                || cafeDto.getName() == null || cafeDto.getName().isBlank()
                || cafeDto.getAddress() == null || cafeDto.getAddress().isBlank()
                || cafeDto.getCity() == null || cafeDto.getCity().isBlank()) {
            throw new Ra1nException(
                    "Invalid cafe data", HttpStatus.BAD_REQUEST
            );
        }
        CafeDto createdCafe = cafeService.addCafe(cafeDto);
        return new ResponseEntity<>(createdCafe, HttpStatus.CREATED);
    }
}
