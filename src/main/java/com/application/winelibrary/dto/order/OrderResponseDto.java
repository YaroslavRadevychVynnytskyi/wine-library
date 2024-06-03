package com.application.winelibrary.dto.order;

import com.application.winelibrary.dto.orderitem.OrderItemDto;
import com.application.winelibrary.entity.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderResponseDto(
        Long id,
        Long userId,
        Set<OrderItemDto> orderItems,
        LocalDateTime orderDate,
        BigDecimal total,
        Order.Status status
) {
}
