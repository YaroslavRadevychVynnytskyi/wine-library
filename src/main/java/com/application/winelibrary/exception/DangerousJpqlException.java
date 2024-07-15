package com.application.winelibrary.exception;

public class DangerousJpqlException extends RuntimeException {
    public DangerousJpqlException(String message) {
        super(message);
    }
}
