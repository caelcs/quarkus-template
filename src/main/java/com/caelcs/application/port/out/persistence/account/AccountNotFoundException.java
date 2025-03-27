package com.caelcs.application.port.out.persistence.account;

import com.caelcs.model.account.AccountType;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String accountNumber, AccountType accountType) {
        super(String.format("Account Entity Not Found. AccountNumber: %s, AccountType: %s", accountNumber, accountType.name()));
    }

}
