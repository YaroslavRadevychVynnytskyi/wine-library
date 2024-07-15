package com.application.winelibrary.controller;

import com.application.winelibrary.dto.verification.RetrieveVerificationCodeRequestDto;
import com.application.winelibrary.dto.verification.SendVerificationCodeRequestDto;
import com.application.winelibrary.dto.verification.VerificationResponseDto;
import com.application.winelibrary.service.verification.PhoneVerificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "SMS verification management", description = "Endpoints for SMS-order verification")
@RestController
@RequestMapping("/verification")
@RequiredArgsConstructor
@Profile("!test")
public class VerificationController {
    private final PhoneVerificationService verificationService;

    @PostMapping("/send")
    @Operation(summary = "Send verification code", description = "Everyone access. "
            + "Sends a SMS code to user's phone number")
    public void sendRequestForVerificationCode(
            @RequestBody RetrieveVerificationCodeRequestDto requestDto) {
        verificationService.sendVerificationCode(requestDto.phoneNumber());
    }

    @PostMapping("/verify")
    @Operation(summary = "Verify order", description = "Everyone access. Sends verification "
            + "code from SMS to server and validates it")
    public VerificationResponseDto verifyCode(
            @RequestBody SendVerificationCodeRequestDto requestDto) {
        return verificationService.verifyCode(requestDto);
    }
}
