package com.bank.account_api.exceptions;

public class InvalidCpfException extends IllegalArgumentException {
    
    public InvalidCpfException(){
        super("CPF inválido");
    }
}
