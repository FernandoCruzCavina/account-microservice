package com.bank.account_api.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.account_api.dtos.AccountDto;
import com.bank.account_api.enums.AccountType;
import com.bank.account_api.models.AccountModel;
import com.bank.account_api.services.AccountService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping()
    public ResponseEntity<List<AccountModel>> getAllAccounts() {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.findAll());
    }

    @GetMapping("/{idAccount}")
    public ResponseEntity<Object> getOneAccount(@PathVariable(value = "idAccount") Long accountId) {
        Optional<AccountModel> accountModelOptional = accountService.findById(accountId);

        if (!accountModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(accountModelOptional.get());
        }

    }

    @PostMapping()
    public ResponseEntity<Object> createAccount(@RequestBody @Valid AccountDto accountDto) {

        if (accountService.existsByAccountNumber(accountDto.getAccountNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: AccountNumber is already taken!");
        }

        var accountModel = new AccountModel();

        BeanUtils.copyProperties(accountDto, accountModel);

        accountModel.setAccountType(AccountType.STARDART);
        accountModel.setCreateOn(new Date().getTime());
        accountModel.setUpdatedOn(new Date().getTime());

        accountService.save(accountModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(accountModel);
    }

    @PutMapping("/{idAccount}")
    public ResponseEntity<Object> updateAccount(@PathVariable(value = "idAccount") Long idAccount,
            @RequestBody @Valid AccountDto accountDto) {
        Optional<AccountModel> accountModelOptional = accountService.findById(idAccount);

        if (!accountModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found!");
        }

        var accountModel = accountModelOptional.get();

        accountModel.setAccountNumber(accountDto.getAccountNumber());
        accountModel.setBalance(accountDto.getBalance());
        accountModel.setImageUrl(accountDto.getImageUrl());
        accountModel.setUpdatedOn(new Date().getTime());

        accountService.save(accountModel);
        return ResponseEntity.status(HttpStatus.OK).body(accountModel);

    }

    @DeleteMapping("/{idAccount}")
    public ResponseEntity<Object> deleteAccount(@PathVariable(value = "idAccount") Long idAccount) {
        Optional<AccountModel> accountModelOptional = accountService.findById(idAccount);

        if (!accountModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found!");
        } else {
            accountService.delete(accountModelOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("account deleted sucessfully!");
        }

    }

}
