package com.caelcs.adapter.in.rest.account;

import com.caelcs.application.dto.AccountDTO;
import com.caelcs.model.account.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(toBuilder = true)
public record AccountCreateWebModel(
        @NotBlank(message="accountNumber may not be blank") String accountNumber,
        @NotNull(message="accountType may not be absent") AccountType accountType
) {

    AccountDTO toAccountDTO() {
        return AccountCreateWebModelMapper.INSTANCE.toAccountDTOFrom(this);
    }

}
