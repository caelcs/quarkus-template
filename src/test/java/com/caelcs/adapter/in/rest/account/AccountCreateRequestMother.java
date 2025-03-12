package com.caelcs.adapter.in.rest.account;

import com.caelcs.model.account.AccountType;

public class AccountCreateRequestMother {

    public static AccountCreateRequest base() {
        return base("345454535", AccountType.DEBIT);
    }

    public static AccountCreateRequest base(String accountNumber, AccountType accountType) {
        return AccountCreateRequest.builder()
                .accountNumber(accountNumber)
                .accountType(accountType)
                .build();
    }
}
