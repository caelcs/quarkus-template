package com.caelcs.application.port.out.persistence.account;

import com.caelcs.model.account.AccountType;

import java.time.LocalDate;
import java.util.UUID;

public class AccountEntityMother {

    public static AccountEntity base() {
        return new AccountEntity(
                UUID.randomUUID(),
                "234234234234",
                AccountType.DEBIT,
                LocalDate.now());
    }

}
