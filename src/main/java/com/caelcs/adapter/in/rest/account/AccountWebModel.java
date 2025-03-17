package com.caelcs.adapter.in.rest.account;

import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountType;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
@SuppressFBWarnings(value = {"EI2", "EI"}, justification = "This list is safely managed elsewhere")
public record AccountWebModel(UUID id,
                              String accountNumber,
                              AccountType accountType,
                              LocalDate creationDate,
                              List<TransactionWebModel> transactions) {

    public static AccountWebModel fromAccount(Account account) {
        return AccountWebModelMapper.INSTANCE.fromAccount(account);
    }

}
