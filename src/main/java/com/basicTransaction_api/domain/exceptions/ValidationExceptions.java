package com.basicTransaction_api.domain.exceptions;

public class ValidationExceptions extends RuntimeException {
    public ValidationExceptions(String message) {
        super(message);
    }
}
