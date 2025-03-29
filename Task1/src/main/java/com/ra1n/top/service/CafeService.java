package com.ra1n.top.service;

import com.ra1n.top.model.dto.CafeDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Travkin Andrii
 * @version 29.03.2025
 */
public interface CafeService {

    List<CafeDto> getAllCafe(Pageable pageable);

    CafeDto addCafe(CafeDto cafeDto);
}
