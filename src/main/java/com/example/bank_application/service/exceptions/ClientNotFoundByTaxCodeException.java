package com.example.bank_application.service.exceptions;

public class ClientNotFoundByTaxCodeException extends RuntimeException{
    public ClientNotFoundByTaxCodeException(String message) {
        super(message);
    }
}
