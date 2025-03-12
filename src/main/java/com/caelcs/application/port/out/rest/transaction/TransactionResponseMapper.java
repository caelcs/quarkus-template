package com.caelcs.application.port.out.rest.transaction;

import com.caelcs.model.transaction.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionResponseMapper {

    TransactionResponseMapper INSTANCE = Mappers.getMapper(TransactionResponseMapper.class);

    @Mapping(target = "type", source = "type")
    @Mapping(target = "creationDate", source = "creationDate")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "amount", source = "amount")
    Transaction toTransaction(TransactionResponse transactionResponse);

}
