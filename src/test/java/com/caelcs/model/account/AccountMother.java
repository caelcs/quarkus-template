package com.caelcs.model.account;

import org.apache.commons.lang3.RandomStringUtils;

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
        return base(RandomStringUtils.secure().nextNumeric(6), AccountType.DEBIT);
    }
}
