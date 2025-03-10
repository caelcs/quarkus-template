package com.caelcs.adapter.out.persistence;

import com.caelcs.model.account.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
interface AccountEntityMapper {
    AccountEntityMapper INSTANCE = Mappers.getMapper(AccountEntityMapper.class);

    @Mapping(target = "accountNumber", source = "accountNumber")
    @Mapping(target = "accountType", source = "accountType")
    @Mapping(target = "id", source = "id")
    AccountEntity fromAccount(Account account);
}
