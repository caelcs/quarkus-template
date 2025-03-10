package com.caelcs.adapter.out.persistence;

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
