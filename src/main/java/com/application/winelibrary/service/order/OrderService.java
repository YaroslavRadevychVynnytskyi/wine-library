package com.application.winelibrary.service.order;

import com.application.winelibrary.dto.order.OrderResponseDto;
import com.application.winelibrary.dto.order.PlaceOrderRequestDto;
import com.application.winelibrary.dto.order.UpdateStatusRequestDto;
import com.application.winelibrary.dto.orderitem.OrderItemDto;
import java.util.List;

public interface OrderService {
    OrderResponseDto placeOrder(Long userId, PlaceOrderRequestDto requestDto);

    List<OrderResponseDto> retrieveOrderHistory(Long userId);

    List<OrderItemDto> retrieveOrderItemsOfSpecificOrder(Long orderId);

    OrderItemDto retrieveSpecificOrderItemOfSpecificOrder(Long orderId, Long itemId);

    OrderResponseDto updateStatus(Long orderId, UpdateStatusRequestDto requestDto);
}
