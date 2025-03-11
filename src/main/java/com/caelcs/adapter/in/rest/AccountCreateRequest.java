package com.caelcs.adapter.in.rest;

import com.caelcs.application.dto.AccountDTO;
import com.caelcs.model.account.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(toBuilder = true)
public record AccountCreateRequest(@NotBlank(message="accountNumber may not be blank") String accountNumber, @NotNull(message="accountType may not be absent") AccountType accountType) {

    AccountDTO toAccountDTO() {
        return AccountCreateRequestMapper.INSTANCE.toAccountDTOFrom(this);
    }

}
