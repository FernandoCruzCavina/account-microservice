package com.bank.account_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<MessageHandler> handleAccountNotFound(AccountNotFoundException ex) {

        MessageHandler messageException = new MessageHandler(HttpStatus.NOT_FOUND, ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageException);
    }

    @ExceptionHandler(PixNotFoundException.class)
    public ResponseEntity<MessageHandler> handlePixNotFound(PixNotFoundException ex) {

        MessageHandler messageException = new MessageHandler(HttpStatus.NOT_FOUND, ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageException);
    }

    @ExceptionHandler(PixTypeAlreadyExistsException.class)
    public ResponseEntity<MessageHandler> handlePixTypeAlreadyExists(PixTypeAlreadyExistsException ex) {

        MessageHandler messageException = new MessageHandler(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(messageException);
    }

    @ExceptionHandler(InvalidCpfException.class)
    public ResponseEntity<MessageHandler> handleInvalidCpf(InvalidCpfException ex) {

        MessageHandler messageException = new MessageHandler(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(messageException);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<MessageHandler> handleInvalidEmail(InvalidEmailException ex) {

        MessageHandler messageException = new MessageHandler(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(messageException);
    }

    @ExceptionHandler(InvalidNumberPhoneException.class)
    public ResponseEntity<MessageHandler> handleInvalidNumberPhone(InvalidNumberPhoneException ex) {

        MessageHandler messageException = new MessageHandler(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(messageException);
    }

    @ExceptionHandler(InvalidRandomKeyException.class)
    public ResponseEntity<MessageHandler> handleInvalidRandomKey(InvalidRandomKeyException ex) {

        MessageHandler messageException = new MessageHandler(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(messageException);
    }

    @ExceptionHandler(InvalidUnknowKeyException.class)
    public ResponseEntity<MessageHandler> handleInvalidUnknowKey(InvalidUnknowKeyException ex) {

        MessageHandler messageException = new MessageHandler(HttpStatus.BAD_REQUEST, ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageException);
    }

}
