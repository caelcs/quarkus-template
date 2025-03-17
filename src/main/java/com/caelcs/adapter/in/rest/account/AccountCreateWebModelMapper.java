package com.caelcs.adapter.in.rest.account;

import com.caelcs.application.dto.AccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
interface AccountCreateWebModelMapper {
    AccountCreateWebModelMapper INSTANCE = Mappers.getMapper(AccountCreateWebModelMapper.class);

    @Mapping(target = "accountNumber", source = "accountNumber")
    @Mapping(target = "accountType", source = "accountType")
    AccountDTO toAccountDTOFrom(AccountCreateWebModel accountCreateWebModel);

}
