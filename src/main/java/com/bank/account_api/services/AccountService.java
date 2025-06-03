package com.bank.account_api.services;

import java.util.List;
import java.util.Optional;

import com.bank.account_api.models.AccountModel;

public interface AccountService {

    List<AccountModel> findAll();

    Optional<AccountModel> findById(Long accountId);

    AccountModel save(AccountModel accountModel);

    boolean existsByAccountNumber(String accountNumber);

    void delete(AccountModel accountModel);

    AccountModel saveAccount(AccountModel accountModel);

    void deleteAccount(AccountModel accountModel);

    AccountModel updateAccount(AccountModel accountModel);

    Optional<AccountModel> findByPixKey(String pixKey);

    Optional<AccountModel> findByUserId(long userId);
}
