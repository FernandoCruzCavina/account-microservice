package com.bank.account_api.exceptions;

public class PixTypeAlreadyExistsException extends IllegalArgumentException {
    
    public PixTypeAlreadyExistsException(){
        super("O Tipo do pix já existe");
    }
}
