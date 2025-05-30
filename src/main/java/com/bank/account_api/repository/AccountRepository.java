package com.bank.account_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.account_api.models.AccountModel;

public interface AccountRepository extends JpaRepository<AccountModel, Long> {
    boolean existsByAccountNumber(String accountNumber);
}
