package com.bank.account_api.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(long id) {
        super("Account not found with ID: " + id);
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
    
}
