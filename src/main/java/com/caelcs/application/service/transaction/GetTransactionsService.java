package com.caelcs.application.service.transaction;

import com.caelcs.application.port.out.rest.transaction.GetTransactionsCase;
import com.caelcs.application.port.out.rest.transaction.TransactionResponse;
import com.caelcs.application.port.out.rest.transaction.TransactionsClient;
import com.caelcs.application.port.out.rest.transaction.TransactionsResponse;
import com.caelcs.model.account.AccountType;
import com.caelcs.model.transaction.Transaction;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class GetTransactionsService implements GetTransactionsCase {

    final private TransactionsClient transactionsClient;

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Override
    public List<Transaction> getTransactionsByAccount(String accountNumber, AccountType accountType) {
        RestResponse<TransactionsResponse> response = transactionsClient.getTransactionsByAccountNumberAndType(accountNumber, accountType);
        return switch (response.getStatus()) {
            case 200 ->
                response.getEntity().transactions().stream()
                        .map(TransactionResponse::toTransaction)
                        .toList();
            default ->
                List.of();
        };
    }

}
