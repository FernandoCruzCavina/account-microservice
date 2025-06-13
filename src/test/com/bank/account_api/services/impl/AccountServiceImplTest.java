package com.bank.account_api.services.impl;

import com.bank.account_api.dtos.AccountDto;
import com.bank.account_api.enums.ActionType;
import com.bank.account_api.enums.AccountType;
import com.bank.account_api.exceptions.AccountNotFoundException;
import com.bank.account_api.models.AccountModel;
import com.bank.account_api.publishers.AccountEventPublisher;
import com.bank.account_api.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @Mock
    AccountRepository accountRepository;
    @Mock
    AccountEventPublisher accountEventPublisher;

    @InjectMocks
    AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_shouldReturnAccount() {
        AccountModel account = new AccountModel();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        AccountModel result = accountService.findById(1L);

        assertEquals(account, result);
    }

    @Test
    void findById_shouldThrowAccountNotFoundException() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.findById(1L));
    }

    @Test
    void saveAccount_shouldSaveAndPublishEvent() {
        AccountModel account = new AccountModel();
        when(accountRepository.save(account)).thenReturn(account);

        AccountModel result = accountService.saveAccount(account);

        assertEquals(account, result);
        verify(accountEventPublisher, times(2)).publishAccountEvent(any(), eq(ActionType.CREATE));
    }

    @Test
    void updateAccount_shouldUpdateAndPublishEvent() {
        Long idAccount = 1L;
        AccountDto dto = new AccountDto();
        dto.setBalance(100.0);
        dto.setImageUrl("img.png");

        AccountModel account = new AccountModel();
        account.setIdAccount(idAccount);

        when(accountRepository.findById(idAccount)).thenReturn(Optional.of(account));
        when(accountRepository.save(any())).thenReturn(account);

        AccountModel result = accountService.updateAccount(idAccount, dto);

        assertEquals(account, result);
        assertEquals(100.0, result.getBalance());
        assertEquals("img.png", result.getImageUrl());
        verify(accountEventPublisher, times(2)).publishAccountEvent(any(), eq(ActionType.UPDATE));
    }

    @Test
    void updateAccount_shouldThrowAccountNotFoundException() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.updateAccount(1L, new AccountDto()));
    }

    @Test
    void deleteAccount_shouldDeleteAndPublishEvent() {
        AccountModel account = new AccountModel();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        accountService.deleteAccount(1L);

        verify(accountRepository).delete(account);
        verify(accountEventPublisher).publishAccountEvent(any(), eq(ActionType.DELETE));
    }

    @Test
    void deleteAccount_shouldThrowAccountNotFoundException() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.deleteAccount(1L));
    }

    @Test
    void createAccount_shouldCreateWithDefaults() {
        AccountDto dto = new AccountDto();
        dto.setBalance(50.0);
        dto.setImageUrl("img.png");

        when(accountRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        AccountModel result = accountService.createAccount(dto);

        assertEquals(AccountType.STARDART, result.getAccountType());
        assertEquals(50.0, result.getBalance());
        assertEquals("img.png", result.getImageUrl());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getLastUpdatedAt());
    }

    @Test
    void findByPixKey_shouldReturnAccount() {
        AccountModel account = new AccountModel();
        when(accountRepository.findByPixKey("pixkey")).thenReturn(Optional.of(account));

        AccountModel result = accountService.findByPixKey("pixkey");

        assertEquals(account, result);
    }

    @Test
    void findByPixKey_shouldThrowAccountNotFoundException() {
        when(accountRepository.findByPixKey("pixkey")).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.findByPixKey("pixkey"));
    }

    @Test
    void findByUserId_shouldReturnAccount() {
        AccountModel account = new AccountModel();
        when(accountRepository.findByUser_UserId(10L)).thenReturn(Optional.of(account));

        AccountModel result = accountService.findByUserId(10L);

        assertEquals(account, result);
    }

    @Test
    void findByUserId_shouldThrowAccountNotFoundException() {
        when(accountRepository.findByUser_UserId(10L)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.findByUserId(10L));
    }
}