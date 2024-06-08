package com.application.winelibrary.mapper;

import com.application.winelibrary.config.MapperConfig;
import com.application.winelibrary.dto.payment.PaymentResponseDto;
import com.application.winelibrary.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface PaymentMapper {
    @Mapping(target = "orderId", source = "payment.order.id")
    PaymentResponseDto toDto(Payment payment);
}
