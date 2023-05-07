package com.example.bank_application.service.impl;

import com.example.bank_application.dto.accountDto.*;
import com.example.bank_application.entity.Account;
import com.example.bank_application.entity.Client;
import com.example.bank_application.entity.enums.AccountStatus;
import com.example.bank_application.entity.enums.AccountType;
import com.example.bank_application.mapper.AccountMapper;
import com.example.bank_application.repository.AccountRepository;
import com.example.bank_application.repository.ClientRepository;
import com.example.bank_application.service.exceptions.*;
import com.example.bank_application.service.interf.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImp implements AccountService {
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    @Override
    @Transactional(readOnly = true)
    public AccountDto getAccountById(String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidSearchArgumentException(ErrorMessage.ARGUMENT_IS_WRONG_IMPOSSIBLE);
        }
        return accountMapper.toDto(accountRepository.findAccountById(uuid).orElseThrow(
                () -> new DataNotFoundException((ErrorMessage.ACCOUNT_NOT_FOUND))));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDto> getAllAccounts() {
        List<Account> accountList = accountRepository.getAllBy();
        if (accountList == null) {
            throw new DataNotFoundException(ErrorMessage.ACCOUNTS_NOT_FOUND);
        }
        List<AccountDto> resList = new ArrayList<>(accountMapper.toListDto(accountList));

        return resList;
    }

    @Override
    @Transactional
    public List<AccountDto> getAllAccountsByStatus(String status) {
        List<Account> accountList = getAllEntityAccountsByStatus(status);
        if (accountList == null) {
            throw new DataNotFoundException(ErrorMessage.ACCOUNTS_NOT_FOUND_BY_STATUS);
        }

        return new ArrayList<>(accountMapper.toListDto(accountList));
    }

    @Override
    @Transactional
    public AccountAfterCreateDto createNewAccount(AccountCreateDto accountCreateDto, String clientTaxCode) {
        Client client = clientRepository.findClientByTaxCode(clientTaxCode);
        AccountAfterCreateDto accountAfterCreateDto;
        Account account;
        if (client == null) {
            throw new ClientNotFoundByTaxCodeException(ErrorMessage.CLIENT_NOT_FOUND_BY_TAX_CODE);
        } else if (accountRepository.findAccountByName(accountCreateDto.getName()) != null) {
            throw new AccountAlreadyExistException(ErrorMessage.ACCOUNT_ALREADY_EXISTS);
        } else {
            try {
                account = accountMapper.toEntity(accountCreateDto);
            } catch (IllegalArgumentException e) {
                throw new InvalidSearchArgumentException(ErrorMessage.ARGUMENT_IS_WRONG_IMPOSSIBLE);
            }
        }
        if (account.getBalance() == null) account.setBalance((double) 0);
        if (account.getStatus() == null) account.setStatus(AccountStatus.PENDING);
        if (account.getType() == null) account.setType(AccountType.CURRENT);
        if (account.getName() == null || account.getCurrencyCode() == null) {
            throw new NotEnoughDataToCreateEntity(ErrorMessage.NOT_ENOUGH_INPUT_DATA);
        }
        account.setClient(client);
        accountAfterCreateDto = accountMapper.toDtoAfterCreate(account);
        accountRepository.save(account);
        return accountAfterCreateDto;
    }

    @Override
    @Transactional
    public AccountsListAfterCreateDto blockAccountByProductIdAndStatus(String productId, String status) {
        List<Account> accountsByStatus = getAllEntityAccountsByStatus(status);
        List<Account> accountsByStatusAndProductId = new ArrayList<>();
        try {
            Integer prodId = Integer.valueOf(productId);
            accountsByStatus.forEach(account -> {
                if (Objects.equals(account.getAgreement().getProduct().getId(), prodId)) {
                    account.setStatus(AccountStatus.BLOCKED);
                    account.setUpdatedAt(LocalDateTime.now());
                    accountsByStatusAndProductId.add(account);
                }
            });
        } catch (IllegalArgumentException e) {
            throw new InvalidSearchArgumentException(ErrorMessage.ARGUMENT_IS_WRONG_TYPE_INCORRECT);
        }
        if (accountsByStatusAndProductId.size() == 0) {
            throw new DataNotFoundException(ErrorMessage.ACCOUNTS_NOT_FOUND_BY_STATUS_AND_PRODUCT_ID);
        }
        return new AccountsListAfterCreateDto(accountMapper.toListAfterCreateDto(accountRepository.saveAll(accountsByStatusAndProductId)));
    }

    private List<Account> getAllEntityAccountsByStatus(String status) {
        AccountStatus statusEnum;
        try {
            statusEnum = AccountStatus.valueOf(status.toUpperCase());
        } catch (RuntimeException e) {
            throw new InvalidSearchArgumentException(ErrorMessage.ARGUMENT_IS_WRONG_IMPOSSIBLE);
        }
        return accountRepository.getAllByStatus(statusEnum);
    }
}

