package com.bank.account_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.account_api.dtos.AccountDto;
import com.bank.account_api.models.AccountModel;
import com.bank.account_api.models.PaymentModel;
import com.bank.account_api.services.AccountService;
import com.bank.account_api.services.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping()
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping()
    public ResponseEntity<List<AccountModel>> getAllAccounts() {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.findAll());
    }

    @GetMapping("/{idAccount}")
    public ResponseEntity<AccountModel> getOneAccount(@PathVariable(value = "idAccount") Long accountId) {
        AccountModel accountModel = accountService.findById(accountId);

        return ResponseEntity.status(HttpStatus.OK).body(accountModel);
    }

    @GetMapping("/extrato/{idAccount}")
    public ResponseEntity<List<PaymentModel>> getExtrato(@PathVariable(value = "idAccount") Long idAccount) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(paymentService.findBySenderAccountOrReceiverAccount(idAccount, idAccount));

    }

    @GetMapping("/pix/{pixKey}")
    public ResponseEntity<AccountModel> getAccountByPixKey(@PathVariable(value = "pixKey") String pixKey) {
        AccountModel accountModel = accountService.findByPixKey(pixKey);

        return ResponseEntity.status(HttpStatus.OK).body(accountModel);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<AccountModel> getAccountByUserId(@PathVariable long userId) {
        AccountModel accountModel = accountService.findByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK).body(accountModel);
    }

    @PostMapping()
    public ResponseEntity<AccountModel> createAccount(@RequestBody @Valid AccountDto accountDto) {
        AccountModel accountModel = accountService.createAccount(accountDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(accountModel);
    }

    @PutMapping("/{idAccount}")
    public ResponseEntity<AccountModel> updateAccount(@PathVariable(value = "idAccount") Long idAccount,
            @RequestBody @Valid AccountDto accountDto) {
        AccountModel accountModel= accountService.updateAccount(idAccount, accountDto);
        
        return ResponseEntity.status(HttpStatus.OK).body(accountModel);
    }

    @DeleteMapping("/{idAccount}")
    public ResponseEntity<String> deleteAccount(@PathVariable(value = "idAccount") Long idAccount) {
        accountService.deleteAccount(idAccount);
        
        return ResponseEntity.status(HttpStatus.OK).body("Conta deletada com sucesso!");
    }
}
