package com.application.winelibrary.dto.cartitem;

public record CartItemDto(
        Long id,
        Long wineId,
        String wineName,
        Integer quantity
) {
}
