package com.bank.account_api.controller;

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

import com.bank.account_api.dto.CreateAccountDto;
import com.bank.account_api.dto.UpdateAccountDto;
import com.bank.account_api.dto.ViewAccountDto;
import com.bank.account_api.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Account")
public class AccountController {

        private final AccountService accountService;

        @Autowired
        public AccountController(AccountService accountService) {
                this.accountService = accountService;
        }

        @Operation(description = "Create a new account", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Details of the account to be created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateAccountDto.class), examples = @ExampleObject(name = "new account", value = "{\"balance\": \"2000\"," +
                        "\"accountType\": \"STANDARD\"," +
                        "\"userId\": \"12\"}", description = "Warning: user_id must be valid"))))
        @ApiResponses(@ApiResponse(responseCode = "201", description = "Account created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ViewAccountDto.class), examples = @ExampleObject(value = 
                        "{\"id\": \"1\"," +
                        "\"balance\": \"2000\"," +
                        "\"accountType\": \"STANDARD\"," +
                        "\"userId\": \"12\"}", name = "account created"))))
        @PostMapping("/create")
        public ResponseEntity<ViewAccountDto> createAccount(@RequestBody CreateAccountDto accountRequest) {
                ViewAccountDto accountResponse = accountService.createAccount(accountRequest);
                return ResponseEntity.status(HttpStatus.CREATED).body(accountResponse);
        }

        @Operation(description = "Get account details by ID", parameters = @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID of the account to retrieve", required = true, example = "1"))
        @ApiResponses(@ApiResponse(responseCode = "200", description = "Account retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ViewAccountDto.class), examples = @ExampleObject(value = 
                        "{\"id\": \"1\"," +
                        "\"balance\": \"2000\"," +
                        "\"accountType\": \"STANDARD\"," +
                        "\"userId\": \"12\"}", name = "account details"))))
        @GetMapping("/{id}")
        public ResponseEntity<ViewAccountDto> getAccountById(@PathVariable long id) {
                ViewAccountDto accountResponse = accountService.getAccountById(id);
                return ResponseEntity.status(HttpStatus.OK).body(accountResponse);
        }

        @Operation(description = "Get all accounts")
        @ApiResponses(@ApiResponse(responseCode = "200", description = "List of all accounts", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ViewAccountDto.class), examples = @ExampleObject(value = 
                        "[{\"id\": \"1\"," +
                        "\"balance\": \"2000\"," +
                        "\"accountType\": \"STANDARD\"," +
                        "\"userId\": \"12\"}]", name = "list of accounts"))))
        @GetMapping("/all")
        public ResponseEntity<List<ViewAccountDto>> getAllAccounts() {
                List<ViewAccountDto> accounts = accountService.getAllAccounts();
                return ResponseEntity.status(HttpStatus.OK).body(accounts);
        }

        @Operation(description = "Update an account by ID", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Details to update the account", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateAccountDto.class), examples = @ExampleObject(name = "update account", value = "{\"balance\": \"3000\"}", description = "Only balance can be updated"))), parameters = @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID of the account to update", required = true, example = "1"))
        @ApiResponses(@ApiResponse(responseCode = "200", description = "Account updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ViewAccountDto.class), examples = @ExampleObject(value = 
                        "{\"id\": \"1\"," +
                        "\"balance\": \"3000\"," +
                        "\"accountType\": \"STANDARD\"," +
                        "\"userId\": \"12\"}", name = "updated account"))))
        @PutMapping("/update/{id}")
        public ResponseEntity<ViewAccountDto> updateAccount(@PathVariable long id,
                        @RequestBody UpdateAccountDto accountRequest) {
                ViewAccountDto accountResponse = accountService.updateAccount(id, accountRequest);
                return ResponseEntity.status(HttpStatus.OK).body(accountResponse);
        }

        @Operation(description = "Delete an account by ID", parameters = @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID of the account to delete", required = true, example = "1"))
        @ApiResponses(@ApiResponse(responseCode = "200", description = "Account deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ViewAccountDto.class), examples = @ExampleObject(value = 
                        "{\"id\": \"1\"," +
                        "\"balance\": \"2000\"," +
                        "\"accountType\": \"STANDARD\"," +
                        "\"userId\": \"12\"}", name = "deleted account"))))
        @DeleteMapping("/{id}")
        public ResponseEntity<ViewAccountDto> deleteAccount(@PathVariable long id) {
                ViewAccountDto accountResponse = accountService.deleteAccountById(id);
                return ResponseEntity.status(HttpStatus.OK).body(accountResponse);
        }
}