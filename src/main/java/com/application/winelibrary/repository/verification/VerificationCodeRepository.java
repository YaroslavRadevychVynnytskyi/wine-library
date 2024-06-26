package com.application.winelibrary.repository.verification;

import com.application.winelibrary.entity.VerificationCode;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findByPhoneNumberAndCode(String phoneNumber, String code);

    void deleteByPhoneNumberAndCode(String phoneNumber, String code);
}
