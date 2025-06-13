package com.bank.account_api.exceptions;

public class InvalidEmailException extends IllegalArgumentException {
    
    public InvalidEmailException(){
        super("Email inválido");
    }
}
