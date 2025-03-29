package com.ra1n.top.output.persistent;

import com.ra1n.top.model.entity.Cafe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author Travkin Andrii
 * @version 29.03.2025
 */
@Repository
public interface CafeRepository extends JpaRepository<Cafe, String> {
    Page<Cafe> findAll(Pageable pageable);
}
