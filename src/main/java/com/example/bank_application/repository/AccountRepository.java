package com.example.bank_application.repository;

import com.example.bank_application.entity.Account;
import com.example.bank_application.entity.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findAccountById(UUID id);

    Account findAccountByName(String name);

    List<Account> getAllBy();

    List<Account> getAllByStatus(AccountStatus status);

}
