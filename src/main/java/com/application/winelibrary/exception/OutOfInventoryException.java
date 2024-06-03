package com.application.winelibrary.exception;

public class OutOfInventoryException extends RuntimeException {
    public OutOfInventoryException(String message) {
        super(message);
    }
}
