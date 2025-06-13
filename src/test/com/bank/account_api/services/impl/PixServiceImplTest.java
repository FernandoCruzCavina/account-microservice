package com.bank.account_api.services;

import com.bank.account_api.dtos.PixDto;
import com.bank.account_api.enums.ActionType;
import com.bank.account_api.enums.PixKeyType;
import com.bank.account_api.exceptions.*;
import com.bank.account_api.models.AccountModel;
import com.bank.account_api.models.PixModel;
import com.bank.account_api.publishers.PixEventPublisher;
import com.bank.account_api.repository.AccountRepository;
import com.bank.account_api.repository.PixRepository;
import com.bank.account_api.utils.CpfValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class PixServiceImplTest {

    @Mock
    PixRepository pixRepository;
    @Mock
    PixEventPublisher pixEventPublisher;
    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    PixServiceImpl pixService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void save_shouldSavePixWithValidCpf() {
        PixModel pixModel = new PixModel();
        pixModel.setKeyType(PixKeyType.CPF);
        pixModel.setKey("12345678909");

        when(pixRepository.save(any())).thenReturn(pixModel);

        PixModel result = pixService.save(pixModel);

        assertEquals(pixModel, result);
    }

    @Test
    void save_shouldThrowInvalidCpfException() {
        PixModel pixModel = new PixModel();
        pixModel.setKeyType(PixKeyType.CPF);
        pixModel.setKey("00000000000");

        assertThrows(InvalidCpfException.class, () -> pixService.save(pixModel));
    }

    @Test
    void save_shouldSavePixWithValidPhone() {
        PixModel pixModel = new PixModel();
        pixModel.setKeyType(PixKeyType.CELULAR);
        pixModel.setKey("+5511999999999");

        when(pixRepository.save(any())).thenReturn(pixModel);

        PixModel result = pixService.save(pixModel);

        assertEquals(pixModel, result);
    }

    @Test
    void save_shouldThrowInvalidNumberPhoneException() {
        PixModel pixModel = new PixModel();
        pixModel.setKeyType(PixKeyType.CELULAR);
        pixModel.setKey("12345");

        assertThrows(InvalidNumberPhoneException.class, () -> pixService.save(pixModel));
    }

    @Test
    void save_shouldSavePixWithValidEmail() {
        PixModel pixModel = new PixModel();
        pixModel.setKeyType(PixKeyType.EMAIL);
        pixModel.setKey("test@email.com");

        when(pixRepository.save(any())).thenReturn(pixModel);

        PixModel result = pixService.save(pixModel);

        assertEquals(pixModel, result);
    }

    @Test
    void save_shouldThrowInvalidEmailException() {
        PixModel pixModel = new PixModel();
        pixModel.setKeyType(PixKeyType.EMAIL);
        pixModel.setKey("invalid-email");

        assertThrows(InvalidEmailException.class, () -> pixService.save(pixModel));
    }

    @Test
    void save_shouldSavePixWithRandomKey() {
        PixModel pixModel = new PixModel();
        pixModel.setKeyType(PixKeyType.CHAVEALEATORIA);
        pixModel.setKey(null);

        when(pixRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        PixModel result = pixService.save(pixModel);

        assertNotNull(result.getKey());
        assertEquals(36, result.getKey().length());
    }

    @Test
    void save_shouldThrowInvalidRandomKeyException() {
        PixModel pixModel = new PixModel();
        pixModel.setKeyType(PixKeyType.CHAVEALEATORIA);
        pixModel.setKey("not-a-uuid");

        assertThrows(InvalidRandomKeyException.class, () -> pixService.save(pixModel));
    }

    @Test
    void updatePix_shouldUpdateSuccessfully() {
        Long idAccount = 1L;
        Long idPix = 2L;
        PixDto pixDto = new PixDto();
        pixDto.setKeyType(PixKeyType.EMAIL);
        pixDto.setKey("new@email.com");

        PixModel existingPix = new PixModel();
        existingPix.setIdPix(idPix);
        existingPix.setKeyType(PixKeyType.EMAIL);
        existingPix.setKey("old@email.com");

        AccountModel accountModel = new AccountModel();
        accountModel.setIdAccount(idAccount);

        when(pixRepository.findPixIntoCourse(idAccount, idPix)).thenReturn(Optional.of(existingPix));
        when(accountRepository.findById(idAccount)).thenReturn(Optional.of(accountModel));
        when(pixRepository.findByAccountModel_IdAccountAndKeyType(idAccount, pixDto.getKeyType())).thenReturn(Optional.empty());
        when(pixRepository.findByKey(pixDto.getKey())).thenReturn(Optional.empty());
        when(pixRepository.save(any())).thenReturn(existingPix);

        PixModel result = pixService.updatePix(idAccount, idPix, pixDto);

        assertEquals("new@email.com", result.getKey());
        verify(pixEventPublisher).publishPixEvent(any(), eq(ActionType.UPDATE));
    }

    @Test
    void updatePix_shouldThrowPixNotFoundException() {
        when(pixRepository.findPixIntoCourse(anyLong(), anyLong())).thenReturn(Optional.empty());

        assertThrows(PixNotFoundException.class, () -> pixService.updatePix(1L, 2L, new PixDto()));
    }

    @Test
    void updatePix_shouldThrowPixTypeAlreadyExistsException() {
        Long idAccount = 1L;
        Long idPix = 2L;
        PixDto pixDto = new PixDto();
        pixDto.setKeyType(PixKeyType.EMAIL);
        pixDto.setKey("new@email.com");

        PixModel existingPix = new PixModel();
        existingPix.setIdPix(idPix);

        PixModel otherPix = new PixModel();
        otherPix.setIdPix(99L);

        when(pixRepository.findPixIntoCourse(idAccount, idPix)).thenReturn(Optional.of(existingPix));
        when(accountRepository.findById(idAccount)).thenReturn(Optional.of(new AccountModel()));
        when(pixRepository.findByAccountModel_IdAccountAndKeyType(idAccount, pixDto.getKeyType())).thenReturn(Optional.of(otherPix));

        assertThrows(PixTypeAlreadyExistsException.class, () -> pixService.updatePix(idAccount, idPix, pixDto));
    }

    @Test
    void updatePix_shouldThrowPixKeyAlreadyExistsException() {
        Long idAccount = 1L;
        Long idPix = 2L;
        PixDto pixDto = new PixDto();
        pixDto.setKeyType(PixKeyType.EMAIL);
        pixDto.setKey("new@email.com");

        PixModel existingPix = new PixModel();
        existingPix.setIdPix(idPix);

        PixModel otherPix = new PixModel();
        otherPix.setIdPix(99L);

        when(pixRepository.findPixIntoCourse(idAccount, idPix)).thenReturn(Optional.of(existingPix));
        when(accountRepository.findById(idAccount)).thenReturn(Optional.of(new AccountModel()));
        when(pixRepository.findByAccountModel_IdAccountAndKeyType(idAccount, pixDto.getKeyType())).thenReturn(Optional.empty());
        when(pixRepository.findByKey(pixDto.getKey())).thenReturn(Optional.of(otherPix));

        assertThrows(PixKeyAlreadyExistsException.class, () -> pixService.updatePix(idAccount, idPix, pixDto));
    }
}