package com.bank.account_api.controller;

import com.bank.account_api.dto.CreateAccountDto;
import com.bank.account_api.dto.UpdateAccountDto;
import com.bank.account_api.dto.ViewAccountDto;
import com.bank.account_api.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<ViewAccountDto> createAccount(@RequestBody CreateAccountDto accountRequest) {
        ViewAccountDto accountResponse = accountService.createAccount(accountRequest);
        return ResponseEntity.ok(accountResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewAccountDto> getAccountById(@PathVariable long id) {
        ViewAccountDto accountResponse = accountService.getAccountById(id);
        return ResponseEntity.ok(accountResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ViewAccountDto>> getAllAccounts() {
        List<ViewAccountDto> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ViewAccountDto> updateAccount(@PathVariable long id, @RequestBody UpdateAccountDto accountRequest) {
        ViewAccountDto accountResponse = accountService.updateAccount(id, accountRequest);
        return ResponseEntity.ok(accountResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ViewAccountDto> deleteAccount(@PathVariable long id) {
        ViewAccountDto accountResponse = accountService.deleteAccountById(id);
        return ResponseEntity.ok(accountResponse);
    }
}