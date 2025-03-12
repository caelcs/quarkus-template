package com.caelcs.adapter.in.rest.account;

import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountType;
import com.caelcs.model.transaction.Transaction;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
public record AccountResponse(UUID id, String accountNumber, AccountType accountType, LocalDate creationDate, List<Transaction> transactions) {

    public static AccountResponse fromAccount(Account account) {
        return AccountResponseMapper.INSTANCE.fromAccount(account);
    }

}
