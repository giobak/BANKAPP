package com.example.bank_application.dto;

import lombok.Value;

import java.util.List;

@Value
public class AccountsListAfterCreateDto {
    List<AccountAfterCreateDto> accountAfterCreateDtoList;
}
