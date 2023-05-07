package com.example.bank_application.entity.enums;

public enum ClientStatus {
    ACTIVE("ACTIVE"),
    PENDING("PENDING"),
    REMOVED("REMOVED"),
    BLOCKED("BLOCKED");
    private final String value;
    ClientStatus(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}