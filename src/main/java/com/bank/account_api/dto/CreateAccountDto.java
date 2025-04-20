package com.bank.account_api.dto;

public record CreateAccountDto(
    double balance, 
    String account_type,
    long user_id
) {
    public CreateAccountDto {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        if (account_type == null || account_type.isEmpty()) {
            throw new IllegalArgumentException("Account type cannot be null or empty");
        }
        if (user_id <= 0) {
            throw new IllegalArgumentException("User ID must be greater than 0");
        }
    }
}