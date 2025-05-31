package com.bank.account_api.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AccountEventDto {
    private Long idAccount;

    private String accountNumber;

    private BigDecimal balance;

    private Long createdAt;

    private Long lastUpdatedAt;

    private String imageUrl;

    private String actionType;
}
