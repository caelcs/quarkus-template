package com.caelcs.adapter.in.rest.transaction;

import com.caelcs.model.transaction.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
interface TransactionWebModelMapper {
    TransactionWebModelMapper INSTANCE = Mappers.getMapper(TransactionWebModelMapper.class);

    @Mapping(target = "type", source = "type")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "creationDate", source = "creationDate")
    TransactionWebModel fromTransaction(Transaction transaction);

}
