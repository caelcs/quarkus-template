package com.caelcs.adapter.in.rest.account;

import com.caelcs.model.transaction.Amount;
import com.caelcs.model.transaction.OperationType;
import com.caelcs.model.transaction.Transaction;
import lombok.Builder;

import java.time.LocalDate;

@Builder(toBuilder = true)
public record TransactionWebModel(LocalDate creationDate, String description, Amount amount, OperationType type) {

    public static TransactionWebModel fromTransaction(Transaction transaction) {
        return TransactionWebModelMapper.INSTANCE.fromTransaction(transaction);
    }
}
