package com.application.winelibrary.dto.orderitem;

public record OrderItemDto(
        Long id,
        Long wineId,
        Integer quantity
) {
}
