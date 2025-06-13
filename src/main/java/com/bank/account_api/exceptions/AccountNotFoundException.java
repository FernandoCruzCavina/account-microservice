package com.bank.account_api.exceptions;

public class AccountNotFoundException extends RuntimeException{
    
    public AccountNotFoundException(){
        super("Conta não foi encontrada.");
    }
}
