package com.bank.account_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account_api.enums.ActionType;
import com.bank.account_api.models.AccountModel;
import com.bank.account_api.publishers.AccountEventPublisher;
import com.bank.account_api.repository.AccountRepository;
import com.bank.account_api.services.AccountService;

import jakarta.transaction.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountEventPublisher accountEventPublisher;

    @Override
    public List<AccountModel> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<AccountModel> findById(Long accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public AccountModel save(AccountModel accountModel) {
        return accountRepository.save(accountModel);
    }

    @Override
    public boolean existsByAccountNumber(String accountNumber) {
        return accountRepository.existsByAccountNumber(accountNumber);
    }

    @Override
    public void delete(AccountModel accountModel) {
        accountRepository.delete(accountModel);
    }

    @Override
    public Optional<AccountModel> findByPixKey(String pixKey) {
        return accountRepository.findByPixKey(pixKey);
    }

    @Transactional
    @Override
    public AccountModel saveAccount(AccountModel accountModel) {
        accountModel = save(accountModel);

        accountEventPublisher.publishAccountEvent(accountModel.convertToAccountEventDto(), ActionType.CREATE);
        return accountModel;
    }

}
