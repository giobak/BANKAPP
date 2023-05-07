package com.example.bank_application.dto;

import lombok.Value;

@Value
public class AccountCreateDto {

    String name;

    String type;

    String status;

    String balance;

    String currencyCode;
}
