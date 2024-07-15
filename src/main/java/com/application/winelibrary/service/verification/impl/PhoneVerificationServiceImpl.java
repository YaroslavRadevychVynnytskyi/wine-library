package com.application.winelibrary.service.verification.impl;

import com.application.winelibrary.dto.verification.SendVerificationCodeRequestDto;
import com.application.winelibrary.dto.verification.VerificationResponseDto;
import com.application.winelibrary.entity.Order;
import com.application.winelibrary.entity.VerificationCode;
import com.application.winelibrary.repository.order.OrderRepository;
import com.application.winelibrary.repository.verification.VerificationCodeRepository;
import com.application.winelibrary.service.message.SmsService;
import com.application.winelibrary.service.verification.PhoneVerificationService;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Profile("!test")
public class PhoneVerificationServiceImpl implements PhoneVerificationService {
    private final VerificationCodeRepository verificationCodeRepository;
    private final OrderRepository orderRepository;

    private final SmsService smsService;
    private final Random random;

    @Override
    public void sendVerificationCode(String phoneNumber) {
        String code = generateVerificationCode();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setPhoneNumber(phoneNumber);
        verificationCode.setCode(code);
        verificationCode.setExpirationTime(LocalDateTime.now().plusMinutes(5));

        smsService.sendSms(phoneNumber, "Your Wine Library order verification code is: " + code);

        verificationCodeRepository.save(verificationCode);
    }

    @Override
    @Transactional
    public VerificationResponseDto verifyCode(SendVerificationCodeRequestDto requestDto) {
        Optional<VerificationCode> verificationCode = verificationCodeRepository
                .findByPhoneNumberAndCode(requestDto.phoneNumber(), requestDto.code());

        if (verificationCode.isPresent()
                && verificationCode.get().getExpirationTime().isAfter(LocalDateTime.now())) {
            Order order = getNotVerifiedOrderByOrderIdAndPhoneNumber(requestDto.orderId(),
                    requestDto.phoneNumber());
            order.setVerified(true);
            orderRepository.save(order);

            verificationCodeRepository.deleteByPhoneNumberAndCode(requestDto.phoneNumber(),
                    requestDto.code());
            return new VerificationResponseDto(true);
        }
        return new VerificationResponseDto(false);
    }

    private String generateVerificationCode() {
        int code = 1000 + random.nextInt(8999);
        return String.valueOf(code);
    }

    private Order getNotVerifiedOrderByOrderIdAndPhoneNumber(Long orderId, String phoneNumber) {
        return orderRepository.findNotVerifiedByOrderIdAndPhoneNumber(orderId, phoneNumber)
                .orElseThrow(() -> new EntityNotFoundException("Can't find order with"
                        + " phone number: " + phoneNumber));
    }
}
