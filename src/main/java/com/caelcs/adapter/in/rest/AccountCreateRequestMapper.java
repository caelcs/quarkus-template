package com.caelcs.adapter.in.rest;

import com.caelcs.application.dto.AccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
interface AccountCreateRequestMapper {
    AccountCreateRequestMapper INSTANCE = Mappers.getMapper(AccountCreateRequestMapper.class);

    @Mapping(target = "accountNumber", source = "accountNumber")
    @Mapping(target = "accountType", source = "accountType")
    AccountDTO toAccountDTOFrom(AccountCreateRequest accountCreateRequest);

}
