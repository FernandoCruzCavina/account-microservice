package com.bank.account_api.publishers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bank.account_api.dtos.AccountEventDto;
import com.bank.account_api.enums.ActionType;

@Component
public class AccountEventPublisher {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value(value = "${broker.exchange.accountEventExchange}")
    private String exchangeAccountEvent;

    public void publishAccountEvent(AccountEventDto accountEventDto, ActionType actionType) {
        accountEventDto.setActionType(actionType.toString());
        rabbitTemplate.convertAndSend(exchangeAccountEvent, "", accountEventDto);
    }
}
