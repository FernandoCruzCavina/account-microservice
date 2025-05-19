package com.bank.account_api.dto;

import com.bank.account_api.exception.ValidationUtils;

public record CreateAccountDto(
        double balance,
        String accountType,
        long userId) {

    public CreateAccountDto {
        ValidationUtils.validateBalance(balance);
        ValidationUtils.validateAccountType(accountType);
        ValidationUtils.validateId(userId);
    }
}
