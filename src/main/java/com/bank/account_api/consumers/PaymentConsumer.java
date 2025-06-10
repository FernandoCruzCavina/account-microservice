package com.bank.account_api.consumers;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.bank.account_api.dtos.PaymentEventDto;
import com.bank.account_api.services.PaymentService;

@Component
public class PaymentConsumer {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    PaymentService paymentService;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${broker.queue.paymentEventQueue}", durable = "true"), exchange = @Exchange(value = "${broker.exchange.paymentEventExchange}", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true")))
    public void listenPaymentEvent(@Payload PaymentEventDto paymentEventDto) {
        var paymentModel = paymentEventDto.convertToPaymentModel();
        paymentModel.setReceiverAccount(paymentEventDto.getReceiverAccount());

        paymentService.save(paymentModel);
    }

}
