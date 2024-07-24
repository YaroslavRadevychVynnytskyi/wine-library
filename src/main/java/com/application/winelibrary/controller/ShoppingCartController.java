package com.application.winelibrary.controller;

import com.application.winelibrary.dto.cart.CartResponseDto;
import com.application.winelibrary.dto.wine.AddWineToCartRequestDto;
import com.application.winelibrary.dto.wine.UpdateWineQuantityRequestDto;
import com.application.winelibrary.entity.User;
import com.application.winelibrary.service.cart.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            description = "User access. Adds wine to user's shopping cart")
    public CartResponseDto add(Authentication authentication,
                               @RequestBody AddWineToCartRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return cartService.add(user.getId(), requestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    @Operation(summary = "Retrieve shopping cart",
            description = "User access. Provides user's shopping cart")
    public CartResponseDto retrieveCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return cartService.retrieveCart(user.getId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/items/{cartItemId}")
    @Operation(summary = "Update wine quantity",
            description = "User access. Updates quantity of certain wine in user's cart")
    public CartResponseDto updateQuantity(Authentication authentication,
                                          @PathVariable Long cartItemId,
                                          @RequestBody UpdateWineQuantityRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return cartService.updateQuantity(user.getId(), cartItemId, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/items/{wineId}")
    @Operation(summary = "Remove wine",
            description = "User access. Deletes certain wine from user's cart")
    public ResponseEntity<String> removeWine(Authentication authentication,
                                     @PathVariable Long wineId) {
        User user = (User) authentication.getPrincipal();
        try {
            cartService.removeWine(user.getId(), wineId);
            return ResponseEntity.ok("Wine removed from cart successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/add-favorites")
    @Operation(summary = "Order the whole liked list",
            description = "User access. Puts into cart all liked items")
    public CartResponseDto addLikedItems(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return cartService.addLikedItems(user.getId());
    }
}
