package com.application.winelibrary.service.order.impl;

import com.application.winelibrary.dto.order.OrderResponseDto;
import com.application.winelibrary.dto.order.PlaceOrderRequestDto;
import com.application.winelibrary.dto.order.UpdateStatusRequestDto;
import com.application.winelibrary.dto.orderitem.OrderItemDto;
import com.application.winelibrary.entity.CartItem;
import com.application.winelibrary.entity.Order;
import com.application.winelibrary.entity.OrderItem;
import com.application.winelibrary.entity.User;
import com.application.winelibrary.exception.TotalCalculationException;
import com.application.winelibrary.mapper.CartItemMapper;
import com.application.winelibrary.mapper.OrderMapper;
import com.application.winelibrary.repository.cartitem.CartItemRepository;
import com.application.winelibrary.repository.city.CityRepository;
import com.application.winelibrary.repository.delivery.DeliveryRepository;
import com.application.winelibrary.repository.order.OrderRepository;
import com.application.winelibrary.repository.orderitem.OrderItemRepository;
import com.application.winelibrary.repository.user.UserRepository;
import com.application.winelibrary.service.order.OrderService;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final CityRepository cityRepository;
    private final DeliveryRepository deliveryRepository;
    private final OrderItemRepository orderItemRepository;

    private final CartItemMapper cartItemMapper;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponseDto placeOrder(Long userId, PlaceOrderRequestDto requestDto) {
        Order order = Order.builder()
                .user(getUserById(userId))
                .email(requestDto.email())
                .firstName(requestDto.firstName())
                .lastName(requestDto.lastName())
                .phoneNumber(requestDto.phoneNumber())
                .deliveryType(deliveryRepository.getByType(requestDto.deliveryType()))
                .paymentType(requestDto.paymentType())
                .status(Order.Status.PENDING)
                .orderDate(LocalDateTime.now())
                .city(cityRepository.findByName(requestDto.city()).orElseThrow())
                .shippingAddress(requestDto.shippingAddress())
                .build();
        order.setOrderItems(getOrderItemsByUserId(userId, order));
        order.setOrderAmount(calculateTotal(order.getOrderItems()));

        order.setTotal(order.getOrderAmount().add(order.getDeliveryType().getPrice()));

        cartItemRepository.deleteByShoppingCartId(userId);

        updateWineInventory(order.getOrderItems());
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    @Override
    public List<OrderResponseDto> retrieveOrderHistory(Long userId) {
        List<Order> orders = orderRepository.findOrdersByUserId(userId);
        return orders.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderItemDto> retrieveOrderItemsOfSpecificOrder(Long orderId) {
        return orderItemRepository.findAllOrderItemDtosByOrderId(orderId);
    }

    @Override
    public OrderItemDto retrieveSpecificOrderItemOfSpecificOrder(Long orderId, Long itemId) {
        return orderItemRepository.findSpecificOrderItemDtoOfSpecificOrder(orderId, itemId);
    }

    @Override
    public OrderResponseDto updateStatus(Long orderId, UpdateStatusRequestDto requestDto) {
        Order order = getOrderById(orderId);
        order.setStatus(requestDto.status());
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Can't find user with ID: " + userId));
    }

    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Can't find order with ID: " + orderId));
    }

    private Set<OrderItem> getOrderItemsByUserId(Long userId, Order order) {
        List<CartItem> cartItems = cartItemRepository.findCartItemsByShoppingCartId(userId);
        return cartItems.stream()
                .map(cartItem -> cartItemMapper.toOrderItem(cartItem, order))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private BigDecimal calculateTotal(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> BigDecimal.valueOf(item.getQuantity())
                        .multiply(item.getPrice()))
                .reduce(BigDecimal::add)
                .orElseThrow(() -> new TotalCalculationException("Can't calculate total"));
    }

    private void updateWineInventory(Set<OrderItem> orderItems) {
        orderItems.forEach(item -> item.getWine()
                .setInventory(item.getWine().getInventory() - item.getQuantity()));
    }
}
