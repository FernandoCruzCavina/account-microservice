package com.bank.account_api.services;

import java.util.List;

import com.bank.account_api.dtos.AccountDto;
import com.bank.account_api.models.AccountModel;

public interface AccountService {

    List<AccountModel> findAll();

    AccountModel findById(Long accountId);

    boolean existsByAccountNumber(String accountNumber);

    AccountModel saveAccount(AccountModel accountModel);

    void deleteAccount(Long idAccount);

    AccountModel updateAccount(Long idAccount, AccountDto accountDto);

    AccountModel findByPixKey(String pixKey);

    AccountModel findByUserId(long userId);

    AccountModel createAccount(AccountDto accountModel);
}
