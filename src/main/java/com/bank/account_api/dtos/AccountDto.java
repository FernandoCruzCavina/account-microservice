package com.bank.account_api.dtos;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto {
    private Long idAccount;

    @NotBlank(message = "The Account Number is required")
    @NotNull(message = "The Account Number is cannout be null")
    @Size(min = 8, max = 8)
    private String accountNumber;

    @NotBlank
    @NotNull
    private BigDecimal balance;

    @NotBlank
    private String imageUrl;
}
