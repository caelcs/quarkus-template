package com.caelcs.adapter.in.rest.transaction;

import java.util.List;

public class TransactionsWebModelMother {

    public static TransactionsWebModel base() {
        return TransactionsWebModel.builder()
                .transactions(List.of(TransactionWebModelMother.base()))
                .build();
    }

}
