package com.bank.account_api.dtos;

import lombok.Data;

@Data
public class PixEventDto {
    private Long idPix;

    private String key;

    private Long createdAt;

    private Long lastUpdatedAt;

    private String actionType;

    private Long idAccount;

}
