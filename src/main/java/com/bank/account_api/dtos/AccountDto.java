package com.bank.account_api.dtos;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto {
    private Long idAccount;

    private String accountNumber;

    @NotBlank
    @NotNull
    private BigDecimal balance;

    @NotBlank
    private String imageUrl;
}
