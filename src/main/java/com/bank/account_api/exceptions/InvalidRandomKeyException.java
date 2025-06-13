package com.bank.account_api.exceptions;


public class InvalidRandomKeyException extends IllegalArgumentException {

    public InvalidRandomKeyException(){
        super("Chave Aleátoria inválida");
    }
}