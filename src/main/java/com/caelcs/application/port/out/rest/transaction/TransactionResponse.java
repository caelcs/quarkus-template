package com.caelcs.application.port.out.rest.transaction;

import com.caelcs.model.transaction.Amount;
import com.caelcs.model.transaction.OperationType;
import com.caelcs.model.transaction.Transaction;
import lombok.Builder;

import java.time.LocalDate;

@Builder(toBuilder = true)
public record TransactionResponse(LocalDate creationDate, String description, Amount amount, OperationType type) {

    public static Transaction toTransaction(TransactionResponse transactionResponse) {
        return TransactionResponseMapper.INSTANCE.toTransaction(transactionResponse);
    }
}
