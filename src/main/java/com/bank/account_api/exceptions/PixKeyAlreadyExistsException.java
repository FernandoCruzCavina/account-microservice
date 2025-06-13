package com.bank.account_api.exceptions;

public class PixKeyAlreadyExistsException extends IllegalArgumentException {
    
    public PixKeyAlreadyExistsException(){
        super("Essa chave pix já existe");
    }
}
