package com.caelcs.adapter.in.rest;

import com.caelcs.model.account.AccountType;

public class AccountCreateRequestMother {

    public static AccountCreateRequest base() {
        return AccountCreateRequest.builder()
                .accountNumber("345345")
                .accountType(AccountType.DEBIT)
                .build();
    }
}
