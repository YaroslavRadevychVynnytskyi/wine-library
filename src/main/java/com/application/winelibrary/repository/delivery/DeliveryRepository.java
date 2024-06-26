package com.application.winelibrary.repository.delivery;

import com.application.winelibrary.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Delivery getByType(Delivery.DeliveryType type);
}
