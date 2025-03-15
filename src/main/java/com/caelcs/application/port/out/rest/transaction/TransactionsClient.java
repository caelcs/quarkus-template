package com.caelcs.application.port.out.rest.transaction;

import com.caelcs.model.account.AccountType;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/transactions")
public interface TransactionsClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    RestResponse<TransactionsResponse> getTransactionsByAccountNumberAndType(@QueryParam("accountNumber") String accountNumber,
                                                                             @QueryParam("accountType") AccountType accountType);

}
