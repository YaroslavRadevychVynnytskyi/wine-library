package com.application.winelibrary.exception;

public class ExistingCartItemException extends RuntimeException {
    public ExistingCartItemException(String message) {
        super(message);
    }
}
