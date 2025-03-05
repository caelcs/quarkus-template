package com.caelcs.application.service.account.account;

import com.caelcs.application.port.in.account.CreateAccountUseCase;
import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountType;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountService implements CreateAccountUseCase {

    @Override
    public Account create(String accountNumber, AccountType accountType) {
        return null;
    }

}
