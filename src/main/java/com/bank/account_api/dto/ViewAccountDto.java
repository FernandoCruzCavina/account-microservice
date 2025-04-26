package com.bank.account_api.dto;

public record ViewAccountDto(
        long id,
        double balance,
        long dateOpened,
        String accountType,
        long userId) {
    public ViewAccountDto {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than 0");
        }
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        if (dateOpened <= 0) {
            throw new IllegalArgumentException("Date opened must be greater than 0");
        }
        if (accountType == null || accountType.isEmpty()) {
            throw new IllegalArgumentException("Account type cannot be null or empty");
        }
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be greater than 0");
        }
    }
}