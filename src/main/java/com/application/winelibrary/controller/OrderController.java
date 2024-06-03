package com.application.winelibrary.controller;

import com.application.winelibrary.dto.order.OrderResponseDto;
import com.application.winelibrary.dto.order.PlaceOrderRequestDto;
import com.application.winelibrary.dto.order.UpdateStatusRequestDto;
import com.application.winelibrary.dto.orderitem.OrderItemDto;
import com.application.winelibrary.entity.User;
import com.application.winelibrary.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing orders")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    @Operation(summary = "Place order",
            description = "Creates an order from items in user's cart and saves it")
    public OrderResponseDto placeOrder(Authentication authentication,
                                       @RequestBody @Valid PlaceOrderRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return orderService.placeOrder(user.getId(), requestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    @Operation(summary = "View order history", description = "Provides user's order history")
    public List<OrderResponseDto> retrieveOrderHistory(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.retrieveOrderHistory(user.getId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{orderId}/items")
    @Operation(summary = "View order items in specific order",
            description = "Returns a list of all order items in specific order")
    public List<OrderItemDto> retrieveAllOrderItemsOfSpecificOrder(@PathVariable Long orderId) {
        return orderService.retrieveOrderItemsOfSpecificOrder(orderId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{orderId}/items/{itemId}")
    @Operation(summary = "Get a specific order item from specific order",
            description = "Returns item from order by id")
    public OrderItemDto retrieveSpecificOrderItemOfSpecificOrder(@PathVariable Long orderId,
                                                                 @PathVariable Long itemId) {
        return orderService.retrieveSpecificOrderItemOfSpecificOrder(orderId, itemId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    @Operation(summary = "Update order status",
            description = "Updates status of an order (available for admin)")
    public OrderResponseDto updateStatus(@PathVariable Long id,
                                     @RequestBody UpdateStatusRequestDto requestDto) {
        return orderService.updateStatus(id, requestDto);
    }
}
