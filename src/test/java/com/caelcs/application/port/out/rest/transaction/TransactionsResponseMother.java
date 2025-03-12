package com.caelcs.application.port.out.rest.transaction;

import java.util.List;

public class TransactionsResponseMother {

    public static TransactionsResponse base() {
        return TransactionsResponse.builder()
                .transactions(List.of(TransactionResponseMother.base()))
                .build();
    }

    public static TransactionsResponse baseNoTransactions() {
        return TransactionsResponse.builder()
                .transactions(List.of())
                .build();
    }
}
