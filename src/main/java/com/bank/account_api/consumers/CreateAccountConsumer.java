package com.bank.account_api.consumers;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.bank.account_api.dtos.AccountEventDto;
import com.bank.account_api.dtos.UserEventDto;
import com.bank.account_api.enums.AccountType;
import com.bank.account_api.enums.ActionType;
import com.bank.account_api.enums.CreationType;
import com.bank.account_api.models.AccountModel;
import com.bank.account_api.repository.UserRepository;
import com.bank.account_api.services.AccountService;
import com.bank.account_api.services.UserService;
import com.bank.account_api.utils.AccountNumberGenerator;

@Component
public class CreateAccountConsumer {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AccountService accountService;
    @Autowired
    UserService userService;

    @Autowired
    AccountNumberGenerator accountNumberGenerator;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${broker.queue.accountEventQueue}", durable = "true"), exchange = @Exchange(value = "${broker.exchange.accountEventExchange}", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true")))
    public void listenAccountEvent(@Payload AccountEventDto accountEventDto) {
        var accountModel = accountEventDto.convertToAccountModel();

        switch (ActionType.valueOf(accountEventDto.getActionType())) {
            case PAYMENT:
                System.out.println("Chegou a mensagem: " + accountEventDto);
                Long accountId = accountEventDto.getIdAccount();
                var existingAccount = accountService.findById(accountId);

                if (existingAccount.isPresent()) {
                    accountModel.setAccountType(existingAccount.get().getAccountType());
                    accountService.save(accountModel);
                }
                break;

            // case DELETE: account
            // accountService.delete(accountEventDto.getIdAccount());
            // break;
            default:
                break;
        }
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${broker.queue.paymentSenderEventQueue}", durable = "true"), exchange = @Exchange(value = "${broker.exchange.paymentSenderExchange}", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true")))
    public void listenPaymentSenderEvent(@Payload AccountEventDto accountEventDto) {
        var accountModel = accountEventDto.convertToAccountModel();

        System.out.println("Chegou a mensagem: " + accountEventDto);
        Long accountId = accountEventDto.getIdAccount();
        var existingAccount = accountService.findById(accountId);

        if (existingAccount.isPresent()) {
            accountModel.setAccountNumber(existingAccount.get().getAccountNumber());
            accountModel.setAccountType(existingAccount.get().getAccountType());
            accountService.save(accountModel);
        }
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${broker.queue.paymentReceiveEventQueue}", durable = "true"), exchange = @Exchange(value = "${broker.exchange.paymentReceiveExchange}", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true")))
    public void listenPaymentReceiveEvent(@Payload AccountEventDto accountEventDto) {
        var accountModel = accountEventDto.convertToAccountModel();

        System.out.println("Chegou a mensagem: " + accountEventDto);
        Long accountId = accountEventDto.getIdAccount();
        var existingAccount = accountService.findById(accountId);

        if (existingAccount.isPresent()) {
            accountModel.setAccountNumber(existingAccount.get().getAccountNumber());
            accountModel.setAccountType(existingAccount.get().getAccountType());
            accountService.save(accountModel);
        }
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${broker.queue.userEventQueue}", durable = "true"), exchange = @Exchange(value = "${broker.exchance.userExchange}", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true")))
    public void listenUserEvent(@Payload UserEventDto userEventDto) {
        var userModel = userEventDto.convertToUserModel();
        System.out.println("Recebi o user: " + userEventDto);

        switch (CreationType.valueOf(userEventDto.getCreationType())) {
            case CREATEACCOUNT:
                var accountModel = new AccountModel();
                userModel.setUserId(userEventDto.getIdUser());

                accountModel.setBalance(BigDecimal.ZERO);
                accountModel.setCreatedAt(Instant.now().getEpochSecond());
                accountModel.setLastUpdatedAt(Instant.now().getEpochSecond());
                accountModel.setAccountType(AccountType.STARDART);
                accountModel.setAccountNumber(accountNumberGenerator.generateUniqueAccountNumber());

                accountModel.setUser(userModel);
                userModel.setAccountModel(accountModel);

                accountService.saveAccount(accountModel);
                userService.save(userModel);
                break;
            default:
                break;
        }

    }
    // @RabbitListener(queues = "${broker.queue.create.account}")
    // public void createAccount(@Payload long userId) {

    // var accountModel = new AccountModel();
    // accountModel.setBalance(BigDecimal.valueOf(0));
    // accountModel.setCreatedAt(Instant.now().getEpochSecond());
    // accountModel.setLastUpdatedAt(Instant.now().getEpochSecond());
    // accountModel.setAccountType(AccountType.STARDART);
    // accountModel.setAccountNumber(accountNumberGenerator.generateUniqueAccountNumber());

    // var user = new UserModel(userId, accountModel);
    // accountModel.setUser(user);

    // accountService.save(accountModel);
    // userRepository.save(user);
    // }
}
