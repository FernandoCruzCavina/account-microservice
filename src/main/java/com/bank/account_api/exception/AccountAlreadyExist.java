package com.bank.account_api.exception;

public class AccountAlreadyExist extends RuntimeException {

    public AccountAlreadyExist(String message) {
        super(message);
    }

}
