package com.application.winelibrary.service.verification;

import com.application.winelibrary.dto.verification.VerificationResponseDto;

public interface PhoneVerificationService {
    void sendVerificationCode(String phoneNumber);

    VerificationResponseDto verifyCode(String phoneNumber, String code);
}
