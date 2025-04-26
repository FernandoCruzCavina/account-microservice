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

import com.bank.account_api.dto.CreateAccountDto;
import com.bank.account_api.dto.UpdateAccountDto;
import com.bank.account_api.dto.ViewAccountDto;
import com.bank.account_api.entity.Account;
import com.bank.account_api.mapper.AccountMapper;
import com.bank.account_api.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountService accountService;

    private static Account account;
    private static CreateAccountDto accountRequest;
    private static ViewAccountDto accountResponse;

    @BeforeEach
    void setUp() {
        account = new Account(1L, 1000.0, 1672531199L, "TEST", 1L);
        accountRequest = new CreateAccountDto(1000.0, "TEST", 1L);
        accountResponse = new ViewAccountDto(1L, 1000.0, 1672531199L, "TEST", 1L);
    }

    @Test
    void testCreateAccount() {
        when(accountMapper.toEntity(accountRequest)).thenReturn(account);
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(accountMapper.toResponse(account)).thenReturn(accountResponse);

        ViewAccountDto response = accountService.createAccount(accountRequest);

        assertEquals(accountResponse, response);
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void testDeleteAccountById() {
        long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountMapper.toResponse(account)).thenReturn(accountResponse);

        ViewAccountDto response = accountService.deleteAccountById(accountId);

        assertEquals(accountResponse, response);
        verify(accountRepository).deleteById(accountId);
    }

    @Test
    void testGetAccountById() {
        long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountMapper.toResponse(account)).thenReturn(accountResponse);

        ViewAccountDto response = accountService.getAccountById(accountId);

        assertEquals(accountResponse, response);
        verify(accountRepository).findById(accountId);
    }

    @Test
    void testGetAllAccounts() {
        when(accountRepository.findAll()).thenReturn(List.of(account));
        when(accountMapper.toResponse(account)).thenReturn(accountResponse);

        List<ViewAccountDto> response = accountService.getAllAccounts();

        assertEquals(1, response.size());
        assertEquals(accountResponse, response.get(0));
        verify(accountRepository).findAll();
    }

    @Test
    void testUpdateAccount() {
        long accountId = 1L;
        UpdateAccountDto updateRequest = new UpdateAccountDto(2000.0);
        Account updatedAccount = new Account(1L, 2000.0, 1672531199L, "TEST", 1L);
        ViewAccountDto updatedResponse = new ViewAccountDto(1L, 2000.0, 1672531199L, "TEST", 1L);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(updatedAccount);
        when(accountMapper.toResponse(updatedAccount)).thenReturn(updatedResponse);

        ViewAccountDto response = accountService.updateAccount(accountId, updateRequest);

        assertEquals(updatedResponse, response);
        verify(accountRepository).findById(accountId);
        verify(accountRepository).save(any(Account.class));
    }
}
