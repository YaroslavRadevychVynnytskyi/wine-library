package com.application.winelibrary.repository.orderitem;

import com.application.winelibrary.dto.orderitem.OrderItemDto;
import com.application.winelibrary.entity.OrderItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT new com.application.winelibrary.dto.orderitem.OrderItemDto(orderItem.id, "
            + "orderItem.wine.id, orderItem.quantity) FROM OrderItem orderItem "
            + "WHERE orderItem.order.id = :orderId")
    List<OrderItemDto> findAllOrderItemDtosByOrderId(Long orderId);

    @Query("SELECT new com.application.winelibrary.dto.orderitem.OrderItemDto(orderItem.id, "
            + "orderItem.wine.id, orderItem.quantity) FROM OrderItem orderItem "
            + "WHERE orderItem.id = :itemId AND orderItem.order.id = :orderId")
    OrderItemDto findSpecificOrderItemDtoOfSpecificOrder(Long orderId, Long itemId);
}
