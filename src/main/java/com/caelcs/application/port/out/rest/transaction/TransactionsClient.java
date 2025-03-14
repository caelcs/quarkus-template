package com.caelcs.application.port.out.rest.transaction;

import com.caelcs.model.account.AccountType;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/transactions")
@RegisterRestClient(configKey = "transactions-api")
public interface TransactionsClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    TransactionsResponse getTransactionsByAccountNumberAndType(@QueryParam("accountNumber") String accountNumber,
                                                               @QueryParam("accountType") AccountType accountType);

}
