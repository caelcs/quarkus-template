package com.caelcs.application.port.out.persistence;

import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountType;

import java.util.Optional;

public interface AccountRepository {

    void saveEntity(Account account);

    Optional<Account> findEntityByAccountNumberAndType(String accountNumber, AccountType accountType);
}
