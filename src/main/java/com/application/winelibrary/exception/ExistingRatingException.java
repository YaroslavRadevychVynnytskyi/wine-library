package com.application.winelibrary.exception;

public class ExistingRatingException extends RuntimeException {
    public ExistingRatingException(String message) {
        super(message);
    }
}
