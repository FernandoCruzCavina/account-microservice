package com.bank.account_api.exceptions;

public class InvalidUnknowKeyException extends IllegalArgumentException{
    
    public InvalidUnknowKeyException(){
        super("Tipo de chave desconhecido");
    }
}
