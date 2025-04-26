package com.bank.account_api.dto;

public record CreateAccountDto(
        double balance,
        String accountType,
        long userId) {
    public CreateAccountDto {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        if (accountType == null || accountType.isEmpty()) {
            throw new IllegalArgumentException("Account type cannot be null or empty");
        }
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be greater than 0");
        }
    }
}