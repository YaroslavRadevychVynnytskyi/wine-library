package com.application.winelibrary.repository.order;

import com.application.winelibrary.entity.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"orderItems"})
    List<Order> findOrdersByUserId(Long userId);

    @EntityGraph(attributePaths = {"orderItems"})
    Optional<Order> findById(Long id);
}
