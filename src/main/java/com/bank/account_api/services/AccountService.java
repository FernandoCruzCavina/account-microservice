package com.bank.account_api.services;

import java.util.List;
import java.util.Optional;

import com.bank.account_api.models.AccountModel;

public interface AccountService {

    List<AccountModel> findAll();

    Optional<AccountModel> findById(Long accountId);

    void save(AccountModel accountModel);

    boolean existsByAccountNumber(String accountNumber);

    void delete(AccountModel accountModel);
}
