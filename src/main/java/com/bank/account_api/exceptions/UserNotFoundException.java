package com.bank.account_api.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(){
        super("Usuário não foi encontrado");
    }
}
