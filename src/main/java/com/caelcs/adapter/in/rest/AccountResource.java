package com.caelcs.adapter.in.rest;

import com.caelcs.application.port.in.account.CreateAccountUseCase;
import com.caelcs.model.account.Account;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.reactive.ResponseStatus;

@Path("accounts")
@AllArgsConstructor
public class AccountResource {

    private CreateAccountUseCase createAccountUseCase;

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @ResponseStatus(201)
    public AccountResponse create(@Valid AccountCreateRequest accountCreateRequest) {
        Account account = createAccountUseCase.create(accountCreateRequest.toAccountDTO());
        return AccountResponse.fromAccount(account);
    }
}
