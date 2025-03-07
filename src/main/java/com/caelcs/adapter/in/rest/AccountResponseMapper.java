package com.caelcs.adapter.in.rest;

import com.caelcs.model.account.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
interface AccountResponseMapper {
    AccountResponseMapper INSTANCE = Mappers.getMapper(AccountResponseMapper.class);

    @Mapping(target = "accountNumber", source = "accountNumber")
    @Mapping(target = "accountType", source = "accountType")
    AccountResponse fromAccount(Account account);

}
