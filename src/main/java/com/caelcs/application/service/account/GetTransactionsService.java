package com.caelcs.application.service.account;

import com.caelcs.application.port.in.transaction.GetTransactionsCase;
import com.caelcs.application.port.out.rest.transaction.TransactionResponse;
import com.caelcs.application.port.out.rest.transaction.TransactionsClient;
import com.caelcs.model.account.AccountType;
import com.caelcs.model.transaction.Transaction;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class GetTransactionsService implements GetTransactionsCase {

    private TransactionsClient transactionsClient;

    @Override
    public List<Transaction> getTransactionsByAccount(String accountNumber, AccountType accountType) {
        return transactionsClient.getTransactionsByAccountNumberAndType(accountNumber, accountType)
                .transactions().stream()
                .map(TransactionResponse::toTransaction)
                .toList();
    }

}
