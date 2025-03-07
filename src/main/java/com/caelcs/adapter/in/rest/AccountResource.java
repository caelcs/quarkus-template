package com.caelcs.adapter.in.rest;

import com.caelcs.application.port.in.account.CreateAccountUseCase;
import com.caelcs.model.account.Account;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;

@Path("accounts")
@AllArgsConstructor
public class AccountResource {

    private CreateAccountUseCase createAccountUseCase;

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public AccountResponse create(AccountCreateRequest accountCreateRequest) {
        Account account = createAccountUseCase.create(accountCreateRequest.toAccountDTO());
        return AccountResponse.fromAccount(account);
    }
}
