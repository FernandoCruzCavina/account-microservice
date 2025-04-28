package com.bank.account_api.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class LavinMQConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void queueShouldBeCreated() {
        Queue queue = context.getBean(Queue.class);
        assertThat(queue.getName()).isEqualTo("default.account"); 
    }

    @Test
    void rabbitTemplateShouldBeConfiguredWithMessageConverter() {
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        assertThat(rabbitTemplate.getMessageConverter()).isInstanceOf(Jackson2JsonMessageConverter.class);
    }
}