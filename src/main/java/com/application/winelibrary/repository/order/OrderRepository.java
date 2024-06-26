package com.application.winelibrary.repository.order;

import com.application.winelibrary.entity.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"orderItems", "deliveryType"})
    List<Order> findOrdersByUserId(Long userId);

    @EntityGraph(attributePaths = {"orderItems", "deliveryType"})
    Optional<Order> findById(Long id);

    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems oi JOIN FETCH o.deliveryType dt "
            + "WHERE o.phoneNumber = :phoneNumber AND o.isVerified != true")
    Optional<Order> findNotVerifiedByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
