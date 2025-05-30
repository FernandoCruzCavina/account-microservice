package com.bank.account_api.dto;

import com.bank.account_api.exception.ValidationUtils;

public record UpdateAccountDto(
        double balance) {
    public UpdateAccountDto {
        ValidationUtils.validateBalance(balance);
    }
}
