package com.caelcs.adapter.in.rest.account;

import com.caelcs.model.account.AccountType;
import org.apache.commons.lang3.RandomStringUtils;

public class AccountCreateWebModelMother {

    public static AccountCreateWebModel base() {
        return base(RandomStringUtils.secure().nextNumeric(6), AccountType.DEBIT);
    }

    public static AccountCreateWebModel base(String accountNumber, AccountType accountType) {
        return AccountCreateWebModel.builder()
                .accountNumber(accountNumber)
                .accountType(accountType)
                .build();
    }
}
