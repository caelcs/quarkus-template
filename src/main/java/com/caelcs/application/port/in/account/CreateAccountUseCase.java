package com.caelcs.application.port.in.account;

import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountType;

public interface CreateAccountUseCase {

    Account create(String accountNumber, AccountType accountType);

}
