package com.bank.account_api.mapper;

import org.mapstruct.Mapper;

import com.bank.account_api.dto.CreateAccountDto;
import com.bank.account_api.dto.ViewAccountDto;
import com.bank.account_api.models.AccountModel;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    ViewAccountDto toResponse(AccountModel account);

    AccountModel toEntity(CreateAccountDto accountRequest);

    AccountModel toEntity(ViewAccountDto accountResponse);
}
