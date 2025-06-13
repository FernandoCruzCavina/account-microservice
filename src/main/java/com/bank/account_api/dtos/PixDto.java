package com.bank.account_api.dtos;

import com.bank.account_api.enums.PixKeyType;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PixDto {

    private String key;

    @NotNull
    @NotBlank
    private PixKeyType keyType;

}
