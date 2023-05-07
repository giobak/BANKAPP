package com.example.bank_application.service.impl;

import com.example.bank_application.dto.clientDto.ClientWithBalanceListDto;
import com.example.bank_application.entity.Client;
import com.example.bank_application.mapper.ClientMapper;
import com.example.bank_application.repository.ClientRepository;
import com.example.bank_application.service.exceptions.DataNotFoundException;
import com.example.bank_application.service.exceptions.ErrorMessage;
import com.example.bank_application.service.exceptions.InvalidSearchArgumentException;
import com.example.bank_application.service.interf.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImp implements ClientService {
    final private ClientRepository clientRepository;

    final private ClientMapper clientMapper;


    @Override
    public ClientWithBalanceListDto getListClientsWithBalanceMoreThan(String balance) {
        Double balanceD;
        try {
            balanceD = Double.valueOf(balance);
        } catch (IllegalArgumentException e) {
            throw new InvalidSearchArgumentException(ErrorMessage.ARGUMENT_IS_WRONG_TYPE_INCORRECT);
        }
        List<Client> clientList = clientRepository.findClientsBy(balanceD);
        if (clientList.size()==0) {
            throw new DataNotFoundException(ErrorMessage.CLIENTS_NOT_FOUND);
        }
       return new ClientWithBalanceListDto(clientMapper.toListDtoWithBalance(clientList));
    }
}
