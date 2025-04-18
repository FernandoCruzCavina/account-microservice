package com.bank.account_api.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account_api.entity.Account;
import com.bank.account_api.service.AccountRepository;
import com.bank.account_api.dto.AccountRequest;
import com.bank.account_api.dto.AccountResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountResponse createAccount(AccountRequest accountRequest) {
        Account account = new Account();
        account.setBalance(accountRequest.balance());
        account.setDate_opened(accountRequest.dateOpened());
        account.setAccount_type(accountRequest.accountType());
        Account savedAccount = accountRepository.save(account);
        
        return new AccountResponse(savedAccount.getId(), savedAccount.getBalance(), savedAccount.getDate_opened(), savedAccount.getAccount_type());
    }

    public AccountResponse getAccountById(long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        
        return new AccountResponse(account.getId(), account.getBalance(), account.getDate_opened(), account.getAccount_type());
    }

    public List<AccountResponse> getAllAccounts() {
        
        return accountRepository.findAll().stream()
                .map(account -> new AccountResponse(account.getId(), account.getBalance(), account.getDate_opened(), account.getAccount_type()))
                .collect(Collectors.toList());
    }

    public AccountResponse updateAccount(long id, AccountRequest accountRequest) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        account.setBalance(accountRequest.balance());
        account.setDate_opened(accountRequest.dateOpened());
        account.setAccount_type(accountRequest.accountType());
        Account updatedAccount = accountRepository.save(account);

        return new AccountResponse(updatedAccount.getId(), updatedAccount.getBalance(), updatedAccount.getDate_opened(), updatedAccount.getAccount_type());
    }

    public void deleteAccount(long id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Account not found");
        }

        accountRepository.deleteById(id);
    }    
}
