package com.caelcs.application.dto;

import com.caelcs.model.account.AccountType;

public class AccountDTOMother {

    public static AccountDTO base(String accountNumber, AccountType accountType) {
        return AccountDTO.builder()
                .accountNumber(accountNumber)
                .accountType(accountType)
                .build();
    }
}
