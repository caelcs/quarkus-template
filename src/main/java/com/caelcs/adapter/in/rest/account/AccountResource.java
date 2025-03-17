package com.caelcs.adapter.in.rest.account;

import com.caelcs.application.port.in.account.CreateAccountUseCase;
import com.caelcs.application.port.in.account.GetAccountUseCase;
import com.caelcs.application.port.in.transaction.GetTransactionsCase;
import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountType;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.reactive.ResponseStatus;

@Path("accounts")
@AllArgsConstructor
public class AccountResource {

    private CreateAccountUseCase createAccountUseCase;

    private GetAccountUseCase getAccountUseCase;

    private GetTransactionsCase getTransactionsCase;

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @ResponseStatus(201)
    public AccountWebModel create(@Valid AccountCreateWebModel accountCreateWebModel) {
        Account account = createAccountUseCase.create(accountCreateWebModel.toAccountDTO());
        return AccountWebModel.fromAccount(account);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @ResponseStatus(200)
    public AccountWebModel getAccountByAccountNumberAndType(@QueryParam("accountNumber") String accountNumber,
                                                            @QueryParam("accountType") AccountType accountType) {
        return getAccountUseCase.getAccountByAccountNumberAndType(accountNumber, accountType)
                .map(AccountWebModel::fromAccount)
                .map(it -> it.toBuilder()
                        .transactions(getTransactionsCase.getTransactionsByAccount(accountNumber, accountType).stream()
                                .map(TransactionWebModel::fromTransaction)
                                .toList())
                        .build())
                .orElseThrow(() -> new EntityNotFoundException("Entity Not Found"));
    }
}
