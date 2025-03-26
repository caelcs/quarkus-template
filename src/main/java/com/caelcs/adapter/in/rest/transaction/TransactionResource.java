package com.caelcs.adapter.in.rest.transaction;

import com.caelcs.application.port.out.rest.transaction.GetTransactionsCase;
import com.caelcs.model.account.AccountType;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.reactive.ResponseStatus;

@Path("transactions")
@AllArgsConstructor
public class TransactionResource {

    private GetTransactionsCase getTransactionsCase;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @ResponseStatus(200)
    public TransactionsWebModel getTransactionsByAccountNumberAndType(@QueryParam("accountNumber") String accountNumber,
                                                                      @QueryParam("accountType") AccountType accountType) {
        return TransactionsWebModel.builder()
                .transactions(getTransactionsCase.getTransactionsByAccount(accountNumber, accountType).stream()
                        .map(TransactionWebModel::fromTransaction)
                        .toList())
                .build();
    }
}
