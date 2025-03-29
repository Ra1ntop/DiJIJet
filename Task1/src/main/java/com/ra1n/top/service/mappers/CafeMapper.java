package com.ra1n.top.service.mappers;

import com.ra1n.top.model.dto.CafeDto;
import com.ra1n.top.model.entity.Cafe;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Travkin Andrii
 * @version 29.03.2025
 */
@Mapper
public interface CafeMapper {

    CafeMapper INSTANCE = Mappers.getMapper(CafeMapper.class);

    CafeDto cafeToCafeDTO(Cafe cafe);

    List<CafeDto> cafesToCafeDTOs(List<Cafe> cafes);


    Cafe cafeDTOToCafe(CafeDto cafeDTO);
}

