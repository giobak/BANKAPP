package com.example.bank_application.service.exceptions;

public class InvalidSearchArgumentException extends IllegalArgumentException{
    public InvalidSearchArgumentException(String message) {
        super(message);
    }
}
