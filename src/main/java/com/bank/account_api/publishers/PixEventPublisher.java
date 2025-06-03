package com.bank.account_api.publishers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bank.account_api.dtos.PixEventDto;
import com.bank.account_api.enums.ActionType;

@Component
public class PixEventPublisher {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value(value = "${broker.exchange.pixEvent}")
    private String exchangePixEvent;

    public void publishPixEvent(PixEventDto pixEventDto, ActionType actionType) {
        pixEventDto.setActionType(actionType.toString());
        rabbitTemplate.convertAndSend(exchangePixEvent, "", pixEventDto);
    }
}
