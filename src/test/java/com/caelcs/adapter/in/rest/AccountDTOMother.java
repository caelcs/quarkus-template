package com.caelcs.adapter.in.rest;

import com.caelcs.application.dto.AccountDTO;
import com.caelcs.model.account.AccountType;

public class AccountDTOMother {

    public static AccountDTO base(String accountNumber, AccountType accountType) {
        return AccountDTO.builder()
                .accountNumber(accountNumber)
                .accountType(accountType)
                .build();
    }
}
