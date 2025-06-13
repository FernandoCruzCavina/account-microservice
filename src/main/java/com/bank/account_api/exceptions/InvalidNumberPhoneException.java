package com.bank.account_api.exceptions;

public class InvalidNumberPhoneException extends IllegalArgumentException{
    
    public InvalidNumberPhoneException(){
        super("Número de celular inválido, use DDD + Número");
    }
}
