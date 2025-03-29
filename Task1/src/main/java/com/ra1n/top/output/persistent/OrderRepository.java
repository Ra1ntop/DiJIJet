package com.ra1n.top.output.persistent;

import com.ra1n.top.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Travkin Andrii
 * @version 29.03.2025
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByCustomerName(String customerName);
}
