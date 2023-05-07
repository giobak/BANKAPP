package com.example.bank_application.dto.clientDto;

import com.example.bank_application.dto.clientDto.ClientWithBalanceDto;
import lombok.Value;

import java.util.List;
@Value
public class ClientWithBalanceListDto {
    List<ClientWithBalanceDto> clientWithBalanceDtoList;
}
