package com.ra1n.top.service.mappers;

import com.ra1n.top.model.dto.OrderDto;
import com.ra1n.top.model.dto.OrderItemDto;
import com.ra1n.top.model.entity.Order;
import com.ra1n.top.model.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Travkin Andrii
 * @version 29.03.2025
 */
@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDto orderToOrderDTO(Order order);

    OrderItemDto orderItemToOrderItemDTO(OrderItem orderItem);

    List<OrderItemDto> orderItemsToOrderItemDTOs(List<OrderItem> orderItems);

    Order orderDTOToOrder(OrderDto orderDTO);
}
