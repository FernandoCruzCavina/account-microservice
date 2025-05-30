package com.bank.account_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MessageHandler extends ResponseEntityExceptionHandler {
    private HttpStatus status;
    private String message;

}

