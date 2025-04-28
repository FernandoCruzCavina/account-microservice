package com.bank.account_api.exception;

public class ValidationUtils {
    
    private ValidationUtils() {}
    
    public static void validateBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
    }
    public static void validateAccountType(String accountType) {
        if (accountType == null || accountType.isEmpty()) {
            throw new IllegalArgumentException("Account type cannot be null or empty");
        }
    }
    public static void validateId(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than 0");
        }
    }
    public static void validateDateOpened(long dateOpened) {
        if (dateOpened <= 0) {
            throw new IllegalArgumentException("Date opened must be greater than 0");
        }
    }
}
