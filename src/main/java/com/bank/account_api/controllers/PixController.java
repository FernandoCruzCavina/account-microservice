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

import com.bank.account_api.dtos.PixDto;
import com.bank.account_api.enums.PixKeyType;
import com.bank.account_api.models.AccountModel;
import com.bank.account_api.models.PixModel;
import com.bank.account_api.services.AccountService;
import com.bank.account_api.services.PixService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping()
public class PixController {

    @Autowired
    PixService pixService;

    @Autowired
    AccountService accountService;

    @GetMapping("/account/{idAccount}/pix")
    public ResponseEntity<List<PixModel>> getAllPixs(@PathVariable("idAccount") Long idAccount) {
        return ResponseEntity.status(HttpStatus.OK).body(pixService.findAllByAccount(idAccount));

    }

    @GetMapping("/account/{idAccount}/pix/{idPix}")
    public ResponseEntity<Object> getOnePix(@PathVariable("idAccount") Long idAccount,
            @PathVariable("idPix") Long idPix) {
        Optional<PixModel> pixModelOptional = pixService.findPixIntoCourse(idAccount, idPix);

        if (!pixModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pix not found for this account.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(pixModelOptional.get());

    }

    @DeleteMapping("/account/{idAccount}/pix/{idPix}")
    public ResponseEntity<Object> deletePix(@PathVariable("idAccount") Long idAccount,
            @PathVariable("idPix") Long idPix) {

        Optional<PixModel> pixModelOptional = pixService.findPixIntoCourse(idAccount, idPix);

        if (!pixModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pix not found for this account.");
        } else {
            pixService.delete(pixModelOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Pix deleted successfully");
        }
    }

    @PostMapping("/account/{idAccount}/pix")
    public ResponseEntity<Object> savePix(@PathVariable(value = "idAccount") long idAccount,
            @RequestBody @Valid PixDto pixDto) {

        Optional<AccountModel> accountModeOptional = accountService.findById(idAccount);

        if (!accountModeOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found!");
        }

        var pixModel = new PixModel();

        BeanUtils.copyProperties(pixDto, pixModel);

        pixModel.setPixKeyType(PixKeyType.CPF);
        pixModel.setCreatedAt(new Date().getTime());
        pixModel.setLastUpdatedAt(new Date().getTime());

        pixModel.setAccountModel(accountModeOptional.get());

        pixService.save(pixModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(pixModel);
    }

    @PutMapping("/account/{idAccount}/pix/{idPix}")
    public ResponseEntity<Object> updatePix(@PathVariable("idAccount") Long idAccount,
            @PathVariable("idPix") Long idPix, @RequestBody @Valid PixDto pixDto) {
        Optional<PixModel> pixModelOptional = pixService.findPixIntoCourse(idAccount, idPix);

        if (!pixModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pix not found for this account.");
        }

        PixModel pix = pixModelOptional.get();

        // BeanUtils.copyProperties(pixDto, pixModel);

        pix.setKey(pixDto.getKey());
        pix.setLastUpdatedAt(new Date().getTime());

        pixService.save(pix);
        return ResponseEntity.status(HttpStatus.OK).body(pix);
    }

}
