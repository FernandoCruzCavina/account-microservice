package com.bank.account_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bank.account_api.dto.AccountRequest;
import com.bank.account_api.dto.AccountResponse;
import com.bank.account_api.entity.Account;
import com.bank.account_api.repository.AccountService;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;
    
    @InjectMocks
    private AccountService accountService;

    private static Account account;
    private static AccountRequest accountRequest;
    private static AccountResponse accountResponse;

    @BeforeEach
    void setUp() {
        account = new Account(1, 1000.0, 1672531199, "SAVINGS");
        accountRequest = new AccountRequest(1000.0, 1672531199, "SAVINGS");
        accountResponse = new AccountResponse(1, 1000.0, 1672531199, "SAVINGS");
    }

    @Test
    void testCreateAccount() {
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountResponse response = accountService.createAccount(accountRequest);

        assertEquals(accountResponse, response);
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void testDeleteAccount() {
        long accountId = 1L;
        when(accountRepository.existsById(accountId)).thenReturn(true);

        accountService.deleteAccount(accountId);

        verify(accountRepository).deleteById(accountId);
    }

    @Test
    void testGetAccountById() {
        long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(java.util.Optional.of(account));

        AccountResponse response = accountService.getAccountById(accountId);

        assertEquals(accountResponse, response);
        verify(accountRepository).findById(accountId);

    }

    @Test
    void testGetAllAccounts() {
        when(accountRepository.findAll()).thenReturn(java.util.List.of(account));

        java.util.List<AccountResponse> response = accountService.getAllAccounts();

        assertEquals(1, response.size());
        assertEquals(accountResponse, response.get(0));
        verify(accountRepository).findAll();

    }

    @Test
    void testUpdateAccount() {
        long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(java.util.Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountResponse response = accountService.updateAccount(accountId, accountRequest);

        assertEquals(accountResponse, response);
        verify(accountRepository).findById(accountId);
        verify(accountRepository).save(any(Account.class));

    }
}
