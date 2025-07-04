package com.api.financial.infra.exception;

public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }
}