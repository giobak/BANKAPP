package com.example.bank_application.controller;

import com.example.bank_application.dto.clientDto.ClientWithBalanceListDto;
import com.example.bank_application.service.interf.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    public final ClientService clientService;

    @GetMapping("/balance_more/{balance}")
    @ResponseStatus(HttpStatus.OK)
    public ClientWithBalanceListDto getListClientsWithBalanceMoreThan(@PathVariable("balance") String balance) {
        return clientService.getListClientsWithBalanceMoreThan(balance);
    }
}
