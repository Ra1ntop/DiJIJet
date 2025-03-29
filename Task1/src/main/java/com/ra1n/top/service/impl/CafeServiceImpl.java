package com.ra1n.top.service.impl;

import com.ra1n.top.model.dto.CafeDto;
import com.ra1n.top.model.entity.Cafe;
import com.ra1n.top.output.persistent.CafeRepository;
import com.ra1n.top.service.CafeService;
import com.ra1n.top.service.mappers.CafeMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Travkin Andrii
 * @version 29.03.2025
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CafeServiceImpl implements CafeService {
    private final CafeMapper cafeMapper = CafeMapper.INSTANCE;
    private final CafeRepository cafeRepository;

    @Override
    public List<CafeDto> getAllCafe(Pageable pageable) {
        Page<Cafe> all = cafeRepository.findAll(pageable);
        return cafeMapper.cafesToCafeDTOs(all.get().toList());
    }

    @Override
    public CafeDto addCafe(CafeDto cafeDto) {
        Cafe cafe = cafeMapper.cafeDTOToCafe(cafeDto);
        Cafe save = cafeRepository.save(cafe);
        return cafeMapper.cafeToCafeDTO(save);
    }

}
