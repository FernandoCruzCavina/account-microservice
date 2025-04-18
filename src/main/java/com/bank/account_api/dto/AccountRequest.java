package com.bank.account_api.dto;

public record AccountRequest(double balance, long dateOpened, String accountType) {
}