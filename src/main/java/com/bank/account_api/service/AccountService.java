package com.bank.account_api.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account_api.dto.CreateAccountDto;
import com.bank.account_api.dto.UpdateAccountDto;
import com.bank.account_api.dto.ViewAccountDto;
import com.bank.account_api.entity.Account;
import com.bank.account_api.exception.AccountAlreadyExist;
import com.bank.account_api.exception.AccountNotFoundException;
import com.bank.account_api.mapper.AccountMapper;
import com.bank.account_api.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private AccountMapper accountMapper;

    @Autowired
    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public ViewAccountDto createAccount(CreateAccountDto accountRequest) {
        accountRepository.findByUserId(accountRequest.userId())
                .ifPresent(account -> {
                    throw new AccountAlreadyExist(account.getUserId());
                });

        Account account = accountMapper.toEntity(accountRequest);
        account.setDateOpened(new Date().getTime());
        Account savedAccount = accountRepository.save(account);

        return accountMapper.toResponse(savedAccount);
    }

    public ViewAccountDto getAccountById(long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        return accountMapper.toResponse(account);
    }

    public List<ViewAccountDto> getAllAccounts() {

        return accountRepository.findAll().stream()
                .map(accountMapper::toResponse)
                .toList();
    }

    public ViewAccountDto updateAccount(long id, UpdateAccountDto accountRequest) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        account.setBalance(accountRequest.balance());
        Account updatedAccount = accountRepository.save(account);

        return accountMapper.toResponse(updatedAccount);
    }

    public ViewAccountDto deleteAccountById(long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        accountRepository.deleteById(id);

        return accountMapper.toResponse(account);
    }
}
