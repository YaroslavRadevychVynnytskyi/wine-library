package com.application.winelibrary.service.cart;

import com.application.winelibrary.dto.cart.CartResponseDto;
import com.application.winelibrary.dto.wine.AddWineToCartRequestDto;
import com.application.winelibrary.dto.wine.UpdateWineQuantityRequestDto;

public interface ShoppingCartService {
    CartResponseDto add(Long userId, AddWineToCartRequestDto requestDto);

    CartResponseDto retrieveCart(Long userId);

    CartResponseDto updateQuantity(
            Long userId,
            Long cartItemId,
            UpdateWineQuantityRequestDto requestDto
    );

    void removeWine(Long cartItemId);
}
