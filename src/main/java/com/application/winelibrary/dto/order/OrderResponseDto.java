package com.application.winelibrary.dto.order;

import com.application.winelibrary.dto.orderitem.OrderItemDto;
import com.application.winelibrary.entity.Delivery;
import com.application.winelibrary.entity.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderResponseDto(
        Long id,
        Long userId,
        String email,
        String firstName,
        String lastName,
        String phoneNumber,
        Delivery.DeliveryType deliveryType,
        Order.PaymentType paymentType,
        Order.Status status,
        BigDecimal orderAmount,
        BigDecimal deliveryPrice,
        BigDecimal total,
        LocalDateTime orderDate,
        String city,
        String shippingAddress,
        Set<OrderItemDto> orderItems,
        boolean isVerified
) {
}
