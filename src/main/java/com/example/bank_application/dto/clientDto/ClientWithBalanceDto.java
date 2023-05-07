package com.example.bank_application.dto.clientDto;

import lombok.Value;

@Value
public class ClientWithBalanceDto {
    String status;

    String taxCode;

    String firstName;

    String lastName;

    String email;

    String phone;

}
