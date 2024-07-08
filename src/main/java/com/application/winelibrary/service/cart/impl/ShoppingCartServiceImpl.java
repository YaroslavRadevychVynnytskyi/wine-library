package com.application.winelibrary.service.cart.impl;

import com.application.winelibrary.dto.cart.CartResponseDto;
import com.application.winelibrary.dto.wine.AddWineToCartRequestDto;
import com.application.winelibrary.dto.wine.UpdateWineQuantityRequestDto;
import com.application.winelibrary.entity.CartItem;
import com.application.winelibrary.entity.ShoppingCart;
import com.application.winelibrary.entity.Wine;
import com.application.winelibrary.exception.CartItemNotFoundException;
import com.application.winelibrary.exception.ExistingCartItemException;
import com.application.winelibrary.exception.OutOfInventoryException;
import com.application.winelibrary.mapper.ShoppingCartMapper;
import com.application.winelibrary.repository.cart.ShoppingCartRepository;
import com.application.winelibrary.repository.cartitem.CartItemRepository;
import com.application.winelibrary.repository.wine.WineRepository;
import com.application.winelibrary.service.cart.ShoppingCartService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
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
        checkIfAlreadyInCart(userId, requestDto.wineId());

        Wine wine = getWineById(requestDto.wineId());
        checkInventoryAvailability(wine.getInventory(), requestDto.quantity());

        CartItem cartItem = new CartItem();
        cartItem.setWine(wine);
        cartItem.setQuantity(requestDto.quantity());
        cartItem.setShoppingCart(getCartById(userId));

        wine.setInventory(wine.getInventory() - cartItem.getQuantity());

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
    @Transactional
    public void removeWine(Long userId, Long wineId) throws CartItemNotFoundException {
        Optional<CartItem> cartItemOptional = cartItemRepository
                .findByShoppingCartIdAndWineId(userId, wineId);

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            Wine wine = cartItem.getWine();
            wine.setInventory(wine.getInventory() + cartItem.getQuantity());
            cartItemRepository.deleteByWineId(wineId);
        } else {
            throw new CartItemNotFoundException("Wine with ID: " + wineId
                    + " not found in user's with ID: " + userId + " shopping cart");
        }
    }

    private void checkIfAlreadyInCart(Long userId, Long wineId) {
        Optional<CartItem> item = cartItemRepository.findByShoppingCartIdAndWineId(userId, wineId);
        if (item.isPresent()) {
            throw new ExistingCartItemException("Wine with ID: " + wineId
                    + " is already in user's shopping cart");
        }
    }

    private void checkInventoryAvailability(Integer availableQuantity, Integer cartQuantity) {
        if (cartQuantity > availableQuantity) {
            throw new OutOfInventoryException("Can't add to cart more items than are in stock");
        }
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
