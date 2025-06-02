package com.bank.account_api.consumers;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;

import com.bank.account_api.enums.AccountType;
import com.bank.account_api.models.AccountModel;
import com.bank.account_api.services.impl.AccountServiceImpl;
import com.bank.account_api.utils.AccountNumberGenerator;


public class CreateAccountConsumer {
    @Autowired
    RabbitTemplate rabbitTemplate;

    AccountServiceImpl accountServiceImpl;

    AccountNumberGenerator accountNumberGenerator;

    @RabbitListener(queues = "${broker.queue.create.account}")
    public void createAccount(@Payload long userId){
        var accountModel = new AccountModel();
        accountModel.setBalance(BigDecimal.valueOf(0));
        accountModel.setCreatedAt(Instant.now().getEpochSecond());
        accountModel.setLastUpdatedAt(Instant.now().getEpochSecond());
        accountModel.setAccountType(AccountType.STARDART);

        String newAccountNumber = accountNumberGenerator.generateUniqueAccountNumber();

        accountModel.setAccountNumber(newAccountNumber);
        
        accountServiceImpl.save(accountModel);
    }
}
