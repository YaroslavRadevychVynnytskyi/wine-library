package com.application.winelibrary.service.payment;

import com.application.winelibrary.dto.payment.CancelPaymentResponseDto;
import com.application.winelibrary.dto.payment.PaymentResponseDto;
import java.util.List;

public interface PaymentService {
    PaymentResponseDto createPayment(Long orderId);

    PaymentResponseDto checkSuccessfulPayment(Long orderId);

    CancelPaymentResponseDto cancelPayment(Long orderId);

    List<PaymentResponseDto> getPayments(Long userId);
}
