package com.example.bank_application.entity.enums;

public enum AccountStatus {
    ACTIVE("ACTIVE"),

    PENDING("PENDING"),
    BLOCKED("BLOCKED"),
    REMOVED("REMOVED");
    private final String value;

    AccountStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}