package com.ra1n.top.service.impl;

import com.ra1n.top.model.dto.CafeDto;
import com.ra1n.top.model.entity.Cafe;
import com.ra1n.top.model.exception.Ra1nException;
import com.ra1n.top.output.persistent.CafeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CafeServiceImplTest {

    @Mock
    private CafeRepository cafeRepository;

    @InjectMocks
    private CafeServiceImpl cafeService;

    @Test
    void getAllCafe_nullPageable_throwsException() {
        Ra1nException ex = assertThrows(Ra1nException.class, () -> cafeService.getAllCafe(null));
        assertEquals("Pageable must not be null", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    void getAllCafe_returnsMappedList() {
        Cafe cafe1 = Cafe.builder().id("1").name("Cafe1").address("Addr1").city("City1").build();
        Cafe cafe2 = Cafe.builder().id("2").name("Cafe2").address("Addr2").city("City2").build();
        List<Cafe> cafes = Arrays.asList(cafe1, cafe2);
        Pageable pageable = PageRequest.of(0, 10);
        when(cafeRepository.findAll(pageable)).thenReturn(new PageImpl<>(cafes));
        List<CafeDto> result = cafeService.getAllCafe(pageable);
        assertEquals(2, result.size());
        assertEquals("Cafe1", result.get(0).getName());
        assertEquals("City2", result.get(1).getCity());
    }

    @Test
    void addCafe_invalidData_throwsException() {
        Ra1nException ex = assertThrows(Ra1nException.class, () -> cafeService.addCafe(new CafeDto()));
        assertEquals("Invalid cafe data", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    void addCafe_validData_savesAndReturnsDto() {
        CafeDto dto = CafeDto.builder().name("Cafe").address("Addr").city("City").build();
        Cafe savedCafe = Cafe.builder().id("1").name("Cafe").address("Addr").city("City").build();
        when(cafeRepository.save(any(Cafe.class))).thenReturn(savedCafe);
        CafeDto result = cafeService.addCafe(dto);
        assertEquals("1", result.getId());
        assertEquals("Cafe", result.getName());
    }
}