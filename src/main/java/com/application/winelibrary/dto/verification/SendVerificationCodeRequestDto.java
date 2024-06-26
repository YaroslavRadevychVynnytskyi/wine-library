package com.application.winelibrary.dto.verification;

public record SendVerificationCodeRequestDto(
        String phoneNumber,
        String code
) {
}
