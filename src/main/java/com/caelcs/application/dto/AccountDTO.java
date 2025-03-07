package com.caelcs.application.dto;

import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountType;
import lombok.Builder;

@Builder(toBuilder = true)
public record AccountDTO(String accountNumber, AccountType accountType) {

    public Account toAccount() {
        return AccountDTOMapper.INSTANCE.toAccount(this);
    }

}
