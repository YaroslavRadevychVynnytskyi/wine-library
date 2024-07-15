package com.application.winelibrary.dto.verification;

public record SendVerificationCodeRequestDto(
        Long orderId,
        String phoneNumber,
        String code
) {
}
