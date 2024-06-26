package com.application.winelibrary.dto.order;

import com.application.winelibrary.entity.Delivery;
import com.application.winelibrary.entity.Order;
import com.application.winelibrary.validation.ValidName;
import com.application.winelibrary.validation.ValidPhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record PlaceOrderRequestDto(
        @NotBlank
        @Email
        String email,

        @ValidName
        String firstName,

        @ValidName
        String lastName,

        @NotBlank
        @ValidPhoneNumber
        String phoneNumber,

        @NotBlank
        @Length(min = 3, max = 50)
        String city,

        @NotBlank
        @Length(min = 5, max = 50)
        String shippingAddress,

        @NotNull
        Delivery.DeliveryType deliveryType,

        Order.PaymentType paymentType
) {
}
