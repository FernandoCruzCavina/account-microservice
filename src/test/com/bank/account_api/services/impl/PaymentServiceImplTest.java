package com.bank.account_api.services.impl;

import com.bank.account_api.models.PaymentModel;
import com.bank.account_api.repository.PaymentRepository;
import com.bank.account_api.services.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    @Mock
    PaymentRepository paymentRepository;

    @InjectMocks
    PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_shouldReturnSavedPayment() {
        PaymentModel payment = new PaymentModel();
        when(paymentRepository.save(payment)).thenReturn(payment);

        PaymentModel result = paymentService.save(payment);

        assertEquals(payment, result);
        verify(paymentRepository).save(payment);
    }

    @Test
    void findBySenderAccountOrReceiverAccount_shouldReturnPayments() {
        Long senderId = 1L;
        Long receiverId = 2L;
        List<PaymentModel> payments = Arrays.asList(new PaymentModel(), new PaymentModel());

        when(paymentRepository.findBySenderAccountOrReceiverAccount(senderId, receiverId)).thenReturn(payments);

        List<PaymentModel> result = paymentService.findBySenderAccountOrReceiverAccount(senderId, receiverId);

        assertEquals(2, result.size());
        verify(paymentRepository).findBySenderAccountOrReceiverAccount(senderId, receiverId);
    }

    @Test
    void findBySenderAccountOrReceiverAccount_shouldReturnEmptyList() {
        Long senderId = 1L;
        Long receiverId = 2L;

        when(paymentRepository.findBySenderAccountOrReceiverAccount(senderId, receiverId)).thenReturn(Collections.emptyList());

        List<PaymentModel> result = paymentService.findBySenderAccountOrReceiverAccount(senderId, receiverId);

        assertTrue(result.isEmpty());
        verify(paymentRepository).findBySenderAccountOrReceiverAccount(senderId, receiverId);
    }

    @Test
    void findBySenderAccountOrReceiverAccount_shouldThrowExceptionOnRepositoryError() {
        Long senderId = 1L;
        Long receiverId = 2L;

        when(paymentRepository.findBySenderAccountOrReceiverAccount(senderId, receiverId))
                .thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> paymentService.findBySenderAccountOrReceiverAccount(senderId, receiverId));
        verify(paymentRepository).findBySenderAccountOrReceiverAccount(senderId, receiverId);
    }
}