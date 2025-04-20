package com.bank.account_api.dto;

public record ViewAccountDto(
    long id, 
    double balance, 
    long date_opened, 
    String account_type,
    long user_id 
) {
    public ViewAccountDto {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than 0");
        }
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        if (date_opened <= 0) {
            throw new IllegalArgumentException("Date opened must be greater than 0");
        }
        if (account_type == null || account_type.isEmpty()) {
            throw new IllegalArgumentException("Account type cannot be null or empty");
        }
        if (user_id <= 0) {
            throw new IllegalArgumentException("User ID must be greater than 0");
        }
    }
}