package com.application.winelibrary.repository.cartitem;

import com.application.winelibrary.entity.CartItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findCartItemsByShoppingCartId(Long shoppingCartId);
}
