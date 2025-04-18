package com.bank.account_api.dto;

public record AccountResponse(long id, double balance, long dateOpened, String accountType) {
}