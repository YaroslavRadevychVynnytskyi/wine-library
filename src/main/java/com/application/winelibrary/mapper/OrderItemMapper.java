package com.application.winelibrary.mapper;

import com.application.winelibrary.config.MapperConfig;
import com.application.winelibrary.dto.orderitem.OrderItemDto;
import com.application.winelibrary.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "wineId", source = "wine.id")
    OrderItemDto toDto(OrderItem orderItem);
}
