package com.bank.account_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PixDto {
    private Long IdPIX;
    private String key;

}
