package com.bank.account_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<MessageHandler> handleException(AccountNotFoundException e) {
        MessageHandler messageHandler = new MessageHandler(HttpStatus.NOT_FOUND, e.getMessage());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageHandler);
    }

    @ExceptionHandler(AccountAlreadyExist.class)
    public ResponseEntity<MessageHandler> handleException(AccountAlreadyExist e) {
        MessageHandler messageHandler = new MessageHandler(HttpStatus.BAD_REQUEST, e.getMessage());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageHandler);
    }
    
}
