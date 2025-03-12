package com.caelcs.application.port.out.persistence.account;

import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountType;

import java.util.Optional;

public interface AccountRepository {

    void saveEntity(Account account);

    Optional<AccountEntity> findEntityByAccountNumberAndType(String accountNumber, AccountType accountType);
}
