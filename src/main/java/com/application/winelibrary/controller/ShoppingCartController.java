package com.application.winelibrary.controller;

import com.application.winelibrary.dto.UpdateWineQuantityRequestDto;
import com.application.winelibrary.dto.cart.CartResponseDto;
import com.application.winelibrary.dto.wine.AddWineToCartRequestDto;
import com.application.winelibrary.entity.User;
import com.application.winelibrary.service.cart.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Endpoints for managing shopping cart")
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService cartService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    @Operation(summary = "Add wine to shopping cart",
            description = "Adds wine to user's shopping cart")
    public CartResponseDto add(Authentication authentication,
                               @RequestBody AddWineToCartRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return cartService.add(user.getId(), requestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    @Operation(summary = "Retrieve shopping cart", description = "Provides user's shopping cart")
    public CartResponseDto retrieveCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return cartService.retrieveCart(user.getId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/items/{cartItemId}")
    @Operation(summary = "Update wine quantity",
            description = "Updates quantity of certain wine in user's cart")
    public CartResponseDto updateQuantity(Authentication authentication,
                                          @PathVariable Long cartItemId,
                                          @RequestBody UpdateWineQuantityRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return cartService.updateQuantity(user.getId(), cartItemId, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/items/{cartItemId}")
    @Operation(summary = "Remove wine", description = "Deletes certain wine from user's cart")
    public void removeWine(@PathVariable Long cartItemId) {
        cartService.removeWine(cartItemId);
    }
}
