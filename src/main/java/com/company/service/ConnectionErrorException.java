package com.company.service;

public class ConnectionErrorException extends RuntimeException {
    public ConnectionErrorException(String errorMessage) {
        super(errorMessage);
    }
}
