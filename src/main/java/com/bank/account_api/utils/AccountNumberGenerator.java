package com.bank.account_api.utils;

import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.account_api.repository.AccountRepository;

@Component
public class AccountNumberGenerator {

    @Autowired
    AccountRepository accountRepository;

    Random random = new Random();

    public String generateUniqueAccountNumber() {
        String accountNumber;
        do {
            int number = 10000000 + random.nextInt(90000000);
            accountNumber = String.valueOf(number);
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }
}
