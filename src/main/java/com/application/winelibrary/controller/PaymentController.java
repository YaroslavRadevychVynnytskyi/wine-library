package com.application.winelibrary.controller;

import com.application.winelibrary.dto.payment.CancelPaymentResponseDto;
import com.application.winelibrary.dto.payment.PaymentResponseDto;
import com.application.winelibrary.service.payment.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Payment management", description = "Endpoints for managing payment")
@RestController
@RequestMapping("/payments")
@Profile("!test")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create/{orderId}")
    @Operation(summary = "Create a payment (checkout)", description = "Pay for order endpoint")
    public PaymentResponseDto createPayment(@PathVariable Long orderId) {
        return paymentService.createPayment(orderId);
    }

    @GetMapping("/success")
    @Operation(summary = "Successful payment redirection endpoint for Stripe")
    public PaymentResponseDto checkSuccessfulPayment(@RequestParam Long orderId) {
        return paymentService.checkSuccessfulPayment(orderId);
    }

    @GetMapping("/cancel")
    @Operation(summary = "Cancelled payment redirection endpoint for Stripe")
    public CancelPaymentResponseDto cancelPayment(@RequestParam Long orderId) {
        return paymentService.cancelPayment(orderId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    @Operation(summary = "Retrieve all user's payments by user ID endpoint",
            description = "Available for admin")
    public List<PaymentResponseDto> getPayments(@RequestParam Long userId) {
        return paymentService.getPayments(userId);
    }
}
