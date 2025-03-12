package com.caelcs.application.port.in.account;

import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountType;

import java.util.Optional;

public interface GetAccountUseCase {

    Optional<Account> getAccountByAccountNumberAndType(String accountNumber, AccountType accountType);

}
