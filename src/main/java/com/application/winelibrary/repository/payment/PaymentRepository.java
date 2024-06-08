package com.application.winelibrary.repository.payment;

import com.application.winelibrary.entity.Payment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(Long orderId);

    @Query("SELECT p FROM Payment p WHERE p.order.id IN (:ordersIds)")
    List<Payment> findAllByOrdersIds(@Param("ordersIds") List<Long> ordersIds);
}
