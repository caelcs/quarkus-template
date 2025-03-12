package com.caelcs.model.transaction;

import java.time.LocalDate;
import java.util.UUID;

public class TransactionMother {

    public static Transaction base() {
        return Transaction.builder()
                .id(UUID.randomUUID())
                .amount(AmountMother.base())
                .type(OperationType.DEBIT)
                .description("test")
                .creationDate(LocalDate.now())
                .build();
    }

}
