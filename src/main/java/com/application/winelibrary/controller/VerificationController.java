package com.application.winelibrary.controller;

import com.application.winelibrary.dto.verification.RetrieveVerificationCodeRequestDto;
import com.application.winelibrary.dto.verification.SendVerificationCodeRequestDto;
import com.application.winelibrary.dto.verification.VerificationResponseDto;
import com.application.winelibrary.service.verification.PhoneVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verification")
@RequiredArgsConstructor
@Profile("!test")
public class VerificationController {
    private final PhoneVerificationService verificationService;

    @PostMapping("/send")
    public void sendRequestForVerificationCode(
            @RequestBody RetrieveVerificationCodeRequestDto requestDto) {
        verificationService.sendVerificationCode(requestDto.phoneNumber());
    }

    @PostMapping("/verify")
    public VerificationResponseDto verifyCode(
            @RequestBody SendVerificationCodeRequestDto requestDto) {
        return verificationService.verifyCode(requestDto.phoneNumber(), requestDto.code());
    }
}
