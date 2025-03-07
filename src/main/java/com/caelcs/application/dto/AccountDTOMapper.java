package com.caelcs.application.dto;

import com.caelcs.adapter.in.rest.AccountResponse;
import com.caelcs.model.account.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
interface AccountDTOMapper {
    AccountDTOMapper INSTANCE = Mappers.getMapper(AccountDTOMapper.class);

    @Mapping(target = "accountNumber", source = "accountNumber")
    @Mapping(target = "accountType", source = "accountType")
    Account toAccount(AccountDTO fromAccountDTO);

}
