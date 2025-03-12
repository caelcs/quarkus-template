package com.caelcs.application.port.out.rest.transaction;

import com.caelcs.model.transaction.AmountMother;
import com.caelcs.model.transaction.OperationType;

import java.time.LocalDate;

public class TransactionResponseMother {

    public static TransactionResponse base() {
        return TransactionResponse.builder()
                .type(OperationType.DEBIT)
                .creationDate(LocalDate.now())
                .description("test")
                .amount(AmountMother.base())
                .build();
    }

}
