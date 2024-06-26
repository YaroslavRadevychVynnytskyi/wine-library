package com.application.winelibrary.mapper;

import com.application.winelibrary.config.MapperConfig;
import com.application.winelibrary.dto.order.OrderResponseDto;
import com.application.winelibrary.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "deliveryType", source = "deliveryType.type")
    @Mapping(target = "deliveryPrice", source = "deliveryType.price")
    @Mapping(target = "city", source = "city.name")
    OrderResponseDto toDto(Order order);
}
