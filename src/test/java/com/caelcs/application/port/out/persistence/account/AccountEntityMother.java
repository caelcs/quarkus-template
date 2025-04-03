package com.caelcs.application.port.out.persistence.account;

import com.caelcs.model.account.AccountType;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.util.UUID;

public class AccountEntityMother {

    public static AccountEntity base() {
        return base(RandomStringUtils.secure().nextNumeric(6), AccountType.DEBIT);
    }

    public static AccountEntity base(String accountNumber, AccountType accountType) {
        return new AccountEntity(
                UUID.randomUUID(),
                accountNumber,
                accountType,
                LocalDate.now());
    }
}
