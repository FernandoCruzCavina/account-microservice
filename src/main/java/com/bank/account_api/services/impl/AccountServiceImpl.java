package com.bank.account_api.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account_api.dtos.AccountDto;
import com.bank.account_api.enums.AccountType;
import com.bank.account_api.enums.ActionType;
import com.bank.account_api.exceptions.AccountNotFoundException;
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
    public AccountModel findById(Long accountId) {
        AccountModel accountModel = accountRepository.findById(accountId)
                .orElseThrow(AccountNotFoundException::new);
        
        return accountModel;
    }

    @Override
    public boolean existsByAccountNumber(String accountNumber) {
        return accountRepository.existsByAccountNumber(accountNumber);
    }

    @Override
    public AccountModel findByPixKey(String pixKey) {
        AccountModel accountModel = accountRepository.findByPixKey(pixKey)
                .orElseThrow(AccountNotFoundException::new);
        
        return accountModel;
    }

    @Transactional
    @Override
    public AccountModel saveAccount(AccountModel accountModel) {
        accountModel = accountRepository.save(accountModel);

        accountEventPublisher.publishAccountEvent(accountModel.convertToAccountEventDto(), ActionType.CREATE);
        accountEventPublisher.publishAccountEvent(accountModel.convertToAccountEventDto(), ActionType.CREATE);
        return accountModel;
    }

    @Override
    public AccountModel createAccount(AccountDto accountDto){
        var accountModel = new AccountModel();

        BeanUtils.copyProperties(accountDto, accountModel);

        accountModel.setAccountType(AccountType.STARDART);
        accountModel.setCreatedAt(System.currentTimeMillis());
        accountModel.setLastUpdatedAt(System.currentTimeMillis());

        accountModel = saveAccount(accountModel);

        return accountModel;
    }

    @Override
    public AccountModel findByUserId(long userId) {
        AccountModel accountModel = accountRepository.findByUser_UserId(userId)
                .orElseThrow(AccountNotFoundException::new);
        
        return accountModel;
    }

    @Transactional
    @Override
    public void deleteAccount(Long idAccount) {
        var account = accountRepository.findById(idAccount)
                .orElseThrow(AccountNotFoundException::new);

        accountRepository.delete(account);

        accountEventPublisher.publishAccountEvent(account.convertToAccountEventDto(), ActionType.DELETE);
    }

    @Transactional
    @Override
    public AccountModel updateAccount(Long idAccount, AccountDto accountDto) {

        AccountModel accountModel = accountRepository.findById(idAccount)
                .orElseThrow(AccountNotFoundException::new);

        accountModel.setBalance(accountDto.getBalance());
        accountModel.setImageUrl(accountDto.getImageUrl());
        accountModel.setLastUpdatedAt(System.currentTimeMillis());

        accountModel = accountRepository.save(accountModel);

        accountEventPublisher.publishAccountEvent(accountModel.convertToAccountEventDto(), ActionType.UPDATE);
        accountEventPublisher.publishAccountEvent(accountModel.convertToAccountEventDto(), ActionType.UPDATE);
        return accountModel;
    }

}
