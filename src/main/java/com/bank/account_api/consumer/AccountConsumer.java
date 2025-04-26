package com.bank.account_api.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.account_api.dto.ViewAccountDto;
import com.bank.account_api.service.AccountService;

@Component
public class AccountConsumer {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Autowired
    private AccountService accountService;
    
    private String routingKey = "payment.replies";

    @RabbitListener(queues = "${broker.queue.user.name}")
    public void replyAccount(long id) {
        ViewAccountDto account = accountService.getAccountById(id);

        Message message = rabbitTemplate.getMessageConverter().toMessage(account, null);

        rabbitTemplate.send("", routingKey, message);
    }
}
