package com.caelcs.adapter.in.rest.transaction;

import com.caelcs.model.transaction.AmountMother;
import com.caelcs.model.transaction.OperationType;

import java.time.LocalDate;

public class TransactionWebModelMother {

    public static TransactionWebModel base() {
        return TransactionWebModel.builder()
                .creationDate(LocalDate.now())
                .type(OperationType.DEBIT)
                .amount(AmountMother.base())
                .description("test")
                .build();
    }

}
