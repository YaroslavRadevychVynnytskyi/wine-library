package com.application.winelibrary.service.payment.impl;

import static com.stripe.param.SetupIntentCreateParams.PaymentMethodOptions.AcssDebit.Currency.USD;

import com.application.winelibrary.dto.payment.CancelPaymentResponseDto;
import com.application.winelibrary.dto.payment.PaymentResponseDto;
import com.application.winelibrary.entity.Order;
import com.application.winelibrary.entity.Payment;
import com.application.winelibrary.exception.PaymentException;
import com.application.winelibrary.mapper.PaymentMapper;
import com.application.winelibrary.repository.order.OrderRepository;
import com.application.winelibrary.repository.payment.PaymentRepository;
import com.application.winelibrary.service.payment.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem;
import jakarta.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Profile("!test")
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private static final String PRODUCT_NAME = "Wine library checkout";
    private static final String PRODUCT_DESCRIPTION = "Best wines in country";
    private static final String CANCEL_URL_MESSAGE = "Payment was cancelled, but it can "
            + "be made later (Session is available for 24 hours)";
    private static final int UNIT_AMOUNT_MULTIPLIER = 100;

    @Value("${stripe.success.url}")
    private String successUrl;
    @Value("${stripe.cancel.url}")
    private String cancelUrl;

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    private final PaymentMapper paymentMapper;

    @Override
    public PaymentResponseDto createPayment(Long orderId) {
        checkIfOrderIsPaid(orderId);

        Order order = getOrderById(orderId);

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(UriComponentsBuilder.fromUriString(getSuccessUrl(orderId))
                        .build().toUri().toString())
                .setCancelUrl(UriComponentsBuilder.fromUriString(getCancelUrl(orderId))
                        .build().toUri().toString())
                .addAllLineItem(Collections.singletonList(
                        LineItem.builder()
                                .setPriceData(
                                        LineItem.PriceData.builder()
                                                .setCurrency(USD.getValue())
                                                .setUnitAmount(order.getTotal().longValue()
                                                        * UNIT_AMOUNT_MULTIPLIER)
                                                .setProductData(
                                                        LineItem.PriceData.ProductData.builder()
                                                                .setName(PRODUCT_NAME)
                                                                .setDescription(PRODUCT_DESCRIPTION)
                                                                .build()
                                                )
                                                .build()
                                )
                                .setQuantity(1L)
                                .build()
                ))
                .build();
        Session session;
        try {
            session = Session.create(params);
        } catch (StripeException e) {
            throw new RuntimeException("Failed to create Stripe session", e);
        }

        Payment payment = Payment.builder()
                .status(Payment.Status.PENDING)
                .order(order)
                .sessionUrl(session.getUrl())
                .sessionId(session.getId())
                .amount(order.getTotal())
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        return paymentMapper.toDto(savedPayment);
    }

    @Override
    public PaymentResponseDto checkSuccessfulPayment(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(() ->
                new EntityNotFoundException("Can't find payment with order ID: " + orderId));
        Session session;
        try {
            session = Session.retrieve(payment.getSessionId());
        } catch (StripeException e) {
            throw new RuntimeException("Failed to retrieve Stripe session", e);
        }
        if (session.getStatus().equals("complete")) {
            payment.setStatus(Payment.Status.PAID);
            paymentRepository.save(payment);
            return paymentMapper.toDto(payment);
        }
        throw new PaymentException("Payment is not successful");
    }

    @Override
    public CancelPaymentResponseDto cancelPayment(Long orderId) {
        return new CancelPaymentResponseDto(CANCEL_URL_MESSAGE);
    }

    @Override
    public List<PaymentResponseDto> getPayments(Long userId) {
        List<Order> orders = orderRepository.findOrdersByUserId(userId);
        List<Long> ordersIds = orders.stream()
                .map(Order::getId)
                .toList();
        List<Payment> payments = paymentRepository.findAllByOrdersIds(ordersIds);
        return payments.stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    private void checkIfOrderIsPaid(Long orderId) {
        Optional<Payment> payment = paymentRepository.findByOrderId(orderId);
        if (payment.isPresent() && payment.get().getStatus().equals(Payment.Status.PAID)) {
            throw new PaymentException("Order with ID: " + orderId + " is already paid");
        }
    }

    private String getSuccessUrl(Long orderId) {
        return successUrl + orderId;
    }

    private String getCancelUrl(Long orderId) {
        return cancelUrl + orderId;
    }

    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find order with ID: " + orderId));
    }
}
