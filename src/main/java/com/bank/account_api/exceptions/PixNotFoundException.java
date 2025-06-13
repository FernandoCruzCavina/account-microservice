package com.bank.account_api.exceptions;

public class PixNotFoundException extends RuntimeException {
    public PixNotFoundException(){
        super("Pix não foi encontrado para essa conta.");
    }
}
