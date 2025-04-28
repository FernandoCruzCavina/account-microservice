package com.bank.account_api.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.account_api.dto.ViewAccountDto;
import com.bank.account_api.service.AccountService;

@Component
public class AccountConsumer {

    private RabbitTemplate rabbitTemplate;

    private AccountService accountService;

    @Autowired
    public AccountConsumer(RabbitTemplate rabbitTemplate, AccountService accountService) {
        this.rabbitTemplate = rabbitTemplate;
        this.accountService = accountService;
    }

    private String routingKey = "payment.replies";

    @RabbitListener(queues = "${broker.queue.account}")
    public void replyAccount(long id) {
        ViewAccountDto account = accountService.getAccountById(id);

        rabbitTemplate.convertAndSend("", routingKey, account);
    }
}
