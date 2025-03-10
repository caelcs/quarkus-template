package com.caelcs.adapter.in.rest;

import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountType;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
public record AccountResponse(UUID id, String accountNumber, AccountType accountType, LocalDate creationDate) {

    public static AccountResponse fromAccount(Account account) {
        return AccountResponseMapper.INSTANCE.fromAccount(account);
    }

}
