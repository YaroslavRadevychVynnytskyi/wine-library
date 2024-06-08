package com.application.winelibrary.dto.payment;

import com.application.winelibrary.entity.Payment;
import java.math.BigDecimal;

public record PaymentResponseDto(
        Long id,
        Payment.Status status,
        Long orderId,
        String sessionUrl,
        String sessionId,
        BigDecimal amount
) {
}
