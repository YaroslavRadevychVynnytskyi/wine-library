package com.application.winelibrary.service.message;

public interface SmsService {
    void sendSms(String toPhoneNumber, String message);
}
