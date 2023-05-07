package com.example.bank_application.mapper;

import com.example.bank_application.dto.clientDto.ClientDto;
import com.example.bank_application.dto.clientDto.ClientWithBalanceDto;
import com.example.bank_application.entity.Client;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDto toDto(Client client);

    ClientWithBalanceDto toDtoWithBalance(Client client);

    Client toEntity(ClientDto clientDto);

    List<ClientWithBalanceDto> toListDtoWithBalance(List<Client> clients);
}
