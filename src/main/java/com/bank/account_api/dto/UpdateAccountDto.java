package com.bank.account_api.dto;

public record UpdateAccountDto(
        double balance) {
    public UpdateAccountDto {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
    }
}
