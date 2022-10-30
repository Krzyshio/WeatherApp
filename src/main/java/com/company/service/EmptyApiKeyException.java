package com.company.service;

public class EmptyApiKeyException extends RuntimeException {
    private static final String ERROR_MESSAGE = "API key is undefined. Check if local.properties is filled in correctly";

    public EmptyApiKeyException() {
        super(ERROR_MESSAGE);
    }
}
