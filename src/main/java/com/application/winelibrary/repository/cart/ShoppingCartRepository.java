package com.application.winelibrary.repository.cart;

import com.application.winelibrary.entity.ShoppingCart;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @EntityGraph(attributePaths = {"cartItems", "cartItems.wine"})
    Optional<ShoppingCart> findById(Long id);
}
