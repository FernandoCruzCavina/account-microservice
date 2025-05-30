package com.bank.account_api.dto;

import com.bank.account_api.exception.ValidationUtils;

public record ViewAccountDto(
        long id,
        double balance,
        long dateOpened,
        String accountType,
        long userId) {
    public ViewAccountDto {
        ValidationUtils.validateId(id);
        ValidationUtils.validateBalance(balance);
        ValidationUtils.validateDateOpened(dateOpened);
        ValidationUtils.validateAccountType(accountType);
        ValidationUtils.validateId(userId);
    }
}