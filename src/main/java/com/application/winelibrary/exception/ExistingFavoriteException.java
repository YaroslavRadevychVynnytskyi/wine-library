package com.application.winelibrary.exception;

public class ExistingFavoriteException extends RuntimeException {
    public ExistingFavoriteException(String message) {
        super(message);
    }
}
