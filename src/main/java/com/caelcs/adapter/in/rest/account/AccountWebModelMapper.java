package com.caelcs.adapter.in.rest.account;

import com.caelcs.model.account.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
interface AccountWebModelMapper {
    AccountWebModelMapper INSTANCE = Mappers.getMapper(AccountWebModelMapper.class);

    @Mapping(target = "accountNumber", source = "accountNumber")
    @Mapping(target = "accountType", source = "accountType")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "creationDate", source = "creationDate")
    AccountWebModel fromAccount(Account account);

}
