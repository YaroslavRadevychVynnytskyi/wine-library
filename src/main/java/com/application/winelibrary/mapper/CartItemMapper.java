package com.application.winelibrary.mapper;

import com.application.winelibrary.config.MapperConfig;
import com.application.winelibrary.dto.cartitem.CartItemDto;
import com.application.winelibrary.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(target = "wineId", source = "wine.id")
    @Mapping(target = "wineName", source = "wine.name")
    CartItemDto toDto(CartItem cartItem);
}
