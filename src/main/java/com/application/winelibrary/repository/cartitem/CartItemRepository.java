package com.application.winelibrary.repository.cartitem;

import com.application.winelibrary.entity.CartItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findCartItemsByShoppingCartId(Long shoppingCartId);

    void deleteByShoppingCartId(Long shoppingCartId);

    Optional<CartItem> findByShoppingCartIdAndWineId(Long shoppingCartId, Long wineId);

    void deleteByWineId(Long wineId);
}
