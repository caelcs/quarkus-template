package com.caelcs.adapter.in.rest;

import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountType;

import java.time.LocalDate;
import java.util.UUID;

public class AccountMother {

    public static Account base(String accountNumber, AccountType accountType) {
        return Account.builder()
                .accountNumber(accountNumber)
                .accountType(accountType)
                .creationDate(LocalDate.now())
                .id(UUID.randomUUID()).build();
    }

    public static Account base() {
        return base("343434343", AccountType.DEBIT);
    }
}
