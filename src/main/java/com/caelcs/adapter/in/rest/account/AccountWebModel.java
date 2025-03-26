package com.caelcs.adapter.in.rest.account;

import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountType;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
public record AccountWebModel(UUID id,
                              String accountNumber,
                              AccountType accountType,
                              LocalDate creationDate) {

    public static AccountWebModel fromAccount(Account account) {
        return AccountWebModelMapper.INSTANCE.fromAccount(account);
    }

}
