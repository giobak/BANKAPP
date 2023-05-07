package com.example.bank_application.service.exceptions;

public class ManagerAlreadyExists extends RuntimeException{
    public ManagerAlreadyExists(String message) {
        super(message);
    }
}
