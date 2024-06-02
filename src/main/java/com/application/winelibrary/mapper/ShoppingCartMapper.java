package com.application.winelibrary.mapper;

import com.application.winelibrary.config.MapperConfig;
import com.application.winelibrary.dto.cart.CartResponseDto;
import com.application.winelibrary.entity.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = CartItemMapper.class)
public interface ShoppingCartMapper {
    @Mapping(target = "userId", source = "user.id")
    CartResponseDto toDto(ShoppingCart shoppingCart);
}
