package com.caelcs.adapter.in.rest.account;

import static com.caelcs.adapter.out.rest.MDCClientRequestFilter.CORRELATION_ID;

import java.util.UUID;

import org.jboss.resteasy.reactive.NoCache;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.slf4j.MDC;

import com.caelcs.application.port.in.account.CreateAccountUseCase;
import com.caelcs.application.port.in.account.GetAccountUseCase;
import com.caelcs.application.port.out.persistence.account.AccountNotFoundException;
import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountType;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;

@Path("accounts")
@AllArgsConstructor
public class AccountResource {

    private CreateAccountUseCase createAccountUseCase;

    private GetAccountUseCase getAccountUseCase;

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @ResponseStatus(201)
    @RolesAllowed("user")
    @NoCache
    public AccountWebModel create(@Valid AccountCreateWebModel accountCreateWebModel) {
        MDC.put(CORRELATION_ID, UUID.randomUUID().toString());
        Account account = createAccountUseCase.create(accountCreateWebModel.toAccountDTO());
        return AccountWebModel.fromAccount(account);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @ResponseStatus(200)
    @RolesAllowed("user")
    @NoCache
    public AccountWebModel getAccountByAccountNumberAndType(@QueryParam("accountNumber") String accountNumber,
                                                            @QueryParam("accountType") AccountType accountType) {
        MDC.put(CORRELATION_ID, UUID.randomUUID().toString());
        return getAccountUseCase.getAccountByAccountNumberAndType(accountNumber, accountType)
                .map(AccountWebModel::fromAccount)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber, accountType));
    }
}
