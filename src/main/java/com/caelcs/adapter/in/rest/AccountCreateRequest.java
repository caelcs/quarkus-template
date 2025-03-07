package com.caelcs.adapter.in.rest;

import com.caelcs.application.dto.AccountDTO;
import com.caelcs.model.account.AccountType;
import lombok.Builder;

@Builder(toBuilder = true)
public record AccountCreateRequest(String accountNumber, AccountType accountType) {

    AccountDTO toAccountDTO() {
        return AccountCreateRequestMapper.INSTANCE.toAccountDTOFrom(this);
    }

}
