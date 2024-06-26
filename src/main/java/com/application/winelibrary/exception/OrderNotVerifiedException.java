package com.application.winelibrary.exception;

public class OrderNotVerifiedException extends RuntimeException {
    public OrderNotVerifiedException(String message) {
        super(message);
    }
}
