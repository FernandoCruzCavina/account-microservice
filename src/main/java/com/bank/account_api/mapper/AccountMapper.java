package com.bank.account_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bank.account_api.dto.CreateAccountDto;
import com.bank.account_api.dto.ViewAccountDto;
import com.bank.account_api.entity.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    ViewAccountDto toResponse(Account account);

    Account toEntity(CreateAccountDto accountRequest);

    Account toEntity(ViewAccountDto accountResponse);
}
