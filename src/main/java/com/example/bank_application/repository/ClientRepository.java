package com.example.bank_application.repository;

import com.example.bank_application.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    Client findClientByTaxCode(String taxCode);

    @Query("SELECT distinct c from Client c JOIN c.accounts a where a.balance >=:balance ")
    List<Client> findClientsBy(Double balance);
}
