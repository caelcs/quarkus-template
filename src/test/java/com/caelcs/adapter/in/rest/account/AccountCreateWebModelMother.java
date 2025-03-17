package com.caelcs.adapter.in.rest.account;

import com.caelcs.model.account.AccountType;

public class AccountCreateWebModelMother {

    public static AccountCreateWebModel base() {
        return base("345454535223232", AccountType.DEBIT);
    }

    public static AccountCreateWebModel base(String accountNumber, AccountType accountType) {
        return AccountCreateWebModel.builder()
                .accountNumber(accountNumber)
                .accountType(accountType)
                .build();
    }
}
