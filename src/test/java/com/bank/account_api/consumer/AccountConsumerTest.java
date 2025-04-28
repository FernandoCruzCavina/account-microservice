package com.bank.account_api.consumer;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.bank.account_api.dto.ViewAccountDto;
import com.bank.account_api.service.AccountService;

@ExtendWith(MockitoExtension.class)
class AccountConsumerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountConsumer accountConsumer;

    @Test
    void replyAccountShouldCallServiceAndSendMessage() {
        long accountId = 1L;
        ViewAccountDto accountDto = new ViewAccountDto(1L, 1000.0, 1672531199L, "STANDARD", 12L);
        when(accountService.getAccountById(accountId)).thenReturn(accountDto);

        accountConsumer.replyAccount(accountId);

        verify(accountService).getAccountById(accountId);
        verify(rabbitTemplate).convertAndSend("", "payment.replies", accountDto);
    }
}
