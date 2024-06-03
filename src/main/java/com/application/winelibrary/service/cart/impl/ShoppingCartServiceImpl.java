package com.application.winelibrary.service.cart.impl;

import com.application.winelibrary.dto.UpdateWineQuantityRequestDto;
import com.application.winelibrary.dto.cart.CartResponseDto;
import com.application.winelibrary.dto.wine.AddWineToCartRequestDto;
import com.application.winelibrary.entity.CartItem;
import com.application.winelibrary.entity.ShoppingCart;
import com.application.winelibrary.entity.Wine;
import com.application.winelibrary.mapper.ShoppingCartMapper;
import com.application.winelibrary.repository.cart.ShoppingCartRepository;
import com.application.winelibrary.repository.cartitem.CartItemRepository;
import com.application.winelibrary.repository.wine.WineRepository;
import com.application.winelibrary.service.cart.ShoppingCartService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository cartRepository;
    private final WineRepository wineRepository;

    private final ShoppingCartMapper cartMapper;

    @Override
    @Transactional
    public CartResponseDto add(Long userId, AddWineToCartRequestDto requestDto) {
        CartItem cartItem = new CartItem();
        cartItem.setWine(getWineById(requestDto.wineId()));
        cartItem.setQuantity(requestDto.quantity());
        cartItem.setShoppingCart(getCartById(userId));

        cartItemRepository.save(cartItem);

        return retrieveCart(userId);
    }

    @Override
    public CartResponseDto retrieveCart(Long userId) {
        ShoppingCart cart = getCartById(userId);
        return cartMapper.toDto(cart);
    }

    @Override
    @Transactional
    public CartResponseDto updateQuantity(
            Long userId,
            Long cartItemId,
            UpdateWineQuantityRequestDto requestDto
    ) {
        CartItem cartItem = getCartItemById(cartItemId);
        cartItem.setQuantity(requestDto.quantity());
        cartItemRepository.save(cartItem);
        return retrieveCart(userId);
    }

    @Override
    public void removeWine(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    private Wine getWineById(Long wineId) {
        return wineRepository.findById(wineId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find wine with ID: " + wineId));
    }

    private ShoppingCart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find cart with ID: " + cartId));

    }

    private CartItem getCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find cart item with ID: " + cartItemId));
    }
}
