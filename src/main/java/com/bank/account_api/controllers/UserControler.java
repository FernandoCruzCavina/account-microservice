package com.bank.account_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.account_api.services.UserService;


@RestController
@RequestMapping()
public class UserControler {
    
    @Autowired
    private UserService userService;


    @GetMapping("/user/{idAccount}")
    public ResponseEntity<Object> getMethodName(@PathVariable("idAccount") Long idAccount) {
        var userId = userService.findByAccountId(idAccount);
        
        return ResponseEntity.status(HttpStatus.OK).body(userId);
    }
    
}
