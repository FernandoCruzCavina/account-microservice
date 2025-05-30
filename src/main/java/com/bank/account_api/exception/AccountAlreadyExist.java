package com.bank.account_api.exception;

public class AccountAlreadyExist extends RuntimeException {

    public AccountAlreadyExist(long id) {
        super("Account already exists with user ID: " + id);
    }

    public AccountAlreadyExist(String message) {
        super(message);
    }

}
