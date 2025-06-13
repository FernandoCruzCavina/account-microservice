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

import com.bank.account_api.dtos.PixDto;
import com.bank.account_api.models.PixModel;
import com.bank.account_api.services.AccountService;
import com.bank.account_api.services.PixService;

import jakarta.validation.Valid;

@RestController
@RequestMapping()
public class PixController {

    @Autowired
    PixService pixService;

    @Autowired
    AccountService accountService;

    @GetMapping("/{idAccount}/pix")
    public ResponseEntity<List<PixModel>> getAllPixs(@PathVariable("idAccount") Long idAccount) {
        return ResponseEntity.status(HttpStatus.OK).body(pixService.findAllByAccount(idAccount));

    }

    @GetMapping("/{idAccount}/pix/{idPix}")
    public ResponseEntity<Object> getOnePix(@PathVariable("idAccount") Long idAccount,
            @PathVariable("idPix") Long idPix) {
        PixModel pixModelOptional = pixService.findPixIntoCourse(idAccount, idPix);

        return ResponseEntity.status(HttpStatus.OK).body(pixModelOptional);

    }

    @DeleteMapping("/{idAccount}/pix/{idPix}")
    public ResponseEntity<Object> deletePix(@PathVariable("idAccount") Long idAccount,
            @PathVariable("idPix") Long idPix) {
        pixService.deletePix(idAccount, idPix);
        
        return ResponseEntity.status(HttpStatus.OK).body("Pix foi deletado com sucesso!");
    }

    @PostMapping("/{idAccount}/pix")
    public ResponseEntity<Object> savePix(@PathVariable(value = "idAccount") long idAccount,
            @RequestBody @Valid PixDto pixDto) {
        PixModel pixModel = pixService.savePix(idAccount, pixDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(pixModel);
    }

    @PutMapping("/{idAccount}/pix/{idPix}")
    public ResponseEntity<Object> updatePix(@PathVariable("idAccount") Long idAccount,
            @PathVariable("idPix") Long idPix, @RequestBody @Valid PixDto pixDto) {
        PixModel pixModel = pixService.updatePix(idAccount, idPix, pixDto);

        return ResponseEntity.status(HttpStatus.OK).body(pixModel);
    }

}
