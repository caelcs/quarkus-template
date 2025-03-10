package com.caelcs.application.port.out.persistence;

import com.caelcs.model.account.Account;

public interface AccountRepository {
    void saveEntity(Account account);
}
