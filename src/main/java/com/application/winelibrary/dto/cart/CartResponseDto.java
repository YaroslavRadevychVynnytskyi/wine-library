package com.application.winelibrary.dto.cart;

import com.application.winelibrary.dto.cartitem.CartItemDto;
import java.util.Set;

public record CartResponseDto(
        Long id,
        Long userId,
        Set<CartItemDto> cartItems
) {
}
