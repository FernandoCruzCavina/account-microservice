package com.bank.account_api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.bank.account_api.dto.CreateAccountDto;
import com.bank.account_api.dto.UpdateAccountDto;
import com.bank.account_api.entity.Account;
import com.bank.account_api.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        accountRepository.deleteAll();
    }

    @Test
    void createAccountShouldBeOK() throws Exception {
        CreateAccountDto accountRequest = new CreateAccountDto(2000.0, "STANDARD", 12L);
        String accountRequestJson = objectMapper.writeValueAsString(accountRequest);

        mockMvc.perform(post("/accounts/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(accountRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.balance").value(2000.0))
                .andExpect(jsonPath("$.accountType").value("STANDARD"))
                .andExpect(jsonPath("$.userId").value(12));
    }

    @Test
    void createAccountShouldReturnBadRequestWhenAccountAlreadyExists() throws Exception {
        Account existingAccount = new Account(100.0,1274670760, "STANDARD", 12L);
        CreateAccountDto accountRequest = new CreateAccountDto(2000.0, "STANDARD", 12L);
        String accountRequestJson = objectMapper.writeValueAsString(accountRequest);

        accountRepository.save(existingAccount);
                
        mockMvc.perform(post("/accounts/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(accountRequestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Account already exists with user ID: " + 12L));
    }

    @Test
    void deleteAccountShouldBeOK() throws Exception {
        Account account = new Account(100.0,1274670760, "STANDARD", 12L);        
        Account savedAccount = accountRepository.save(account);

        long accountId = savedAccount.getId();

        mockMvc.perform(delete("/accounts/{id}", accountId))
                .andExpect(status().isOk());
    }

    @Test
    void deleteAccountShouldReturnNotFound() throws Exception {
        long nonExistentAccountId = 999L;

        mockMvc.perform(delete("/accounts/{id}", nonExistentAccountId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Account not found with ID: " + nonExistentAccountId));
    }

    @Test
    void getAccountByIdShouldBeOK() throws Exception {
        Account account = new Account(100.0,1274670760, "STANDARD", 10L);
        Account savedAccount = accountRepository.save(account);

        long accountId = savedAccount.getId();

        mockMvc.perform(get("/accounts/{id}", accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(accountId))
                .andExpect(jsonPath("$.balance").value(100.0))
                .andExpect(jsonPath("$.accountType").value("STANDARD"))
                .andExpect(jsonPath("$.userId").value(10L));
    }

    @Test
    void getAccountByIdShouldReturnNotFound() throws Exception {
        long nonExistentAccountId = 999L;

        mockMvc.perform(get("/accounts/{id}", nonExistentAccountId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Account not found with ID: " + nonExistentAccountId));
    }

    @Test
    void getAllAccountsShouldBeOK() throws Exception {
        mockMvc.perform(get("/accounts/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void updateAccountShouldBeOK() throws Exception {
        Account account = new Account(100.0,1274670760, "STANDARD", 12L);        
        Account savedAccount = accountRepository.save(account);

        long accountId = savedAccount.getId();

        UpdateAccountDto updateRequest = new UpdateAccountDto(3000.0);
        String updateRequestJson = objectMapper.writeValueAsString(updateRequest);

        mockMvc.perform(put("/accounts/update/{id}", accountId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updateRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(3000.0));
    }

    @Test
    void updateAccountShouldReturnNotFound() throws Exception {
        long nonExistentAccountId = 999L;
        UpdateAccountDto updateRequest = new UpdateAccountDto(3000.0);
        String updateRequestJson = objectMapper.writeValueAsString(updateRequest);
        
        mockMvc.perform(put("/accounts/update/{id}", nonExistentAccountId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updateRequestJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Account not found with ID: " + nonExistentAccountId));
    }
}